from typing import Optional
from nats.aio.client import Client as NATS
from dto.variant_dto import VariantDTO
from service.variant_service import VariantService
from datetime import datetime, timedelta
import json

class VariantSagaOrchestrator:
        
    @staticmethod
    async def create_variant_with_price(variant_data: VariantDTO, initial_price: Optional[float] = None, nc: "NATS" = None):
        
        if nc is None:
            raise RuntimeError("NATS connection is missing")
        
        variant = None
        price_list_id_for_rollback = None

        try:
            variant = VariantService.create_variant(variant_data)
            if not variant:
                raise RuntimeError("Kreiranje varijante nije uspjelo")

            today = datetime.now().date()
            expire = today + timedelta(days=30)
            price_list_payload = {
                "price_list_id": "PL-123",
                "title": f"Saga Price List Event for Variant {variant.id}",
                "discount": 15.5,
                "quantity": 2,
                "phaseId": 99,
                "action": "CREATE",
                "event": 1,
                #"event_time": datetime.datetime.now(datetime.timezone.utc).isoformat()
            }
            payload_bytes = json.dumps(price_list_payload).encode('utf-8')

            print(f"[SAGA] Šaljem zahtjev na 'saga.variant.create'")
            response_msg = await nc.request('saga.variant.create', payload_bytes, timeout=15)

            response_data = json.loads(response_msg.data.decode('utf-8'))
            
            if response_data.get("status") == "SUCCESS":
                created_price_list = response_data.get("data")
                price_list_id_for_rollback = created_price_list.get("id")
                print(f"[SAGA] Uspjeh! Kreiran PriceList ID: {price_list_id_for_rollback}")
                return {"variant": variant, "priceList": created_price_list}
            else:
                error_message = response_data.get("error", "Nepoznata greška")
                raise RuntimeError(f"Sales-service vratio grešku: {error_message}")

        except Exception as e:
            print(f"[SAGA] Greška u sagi: {e}. Pokrećem rollback.")
            
            if variant:
                VariantService.delete_variant(variant.product_id, variant.id)
                print(f"[SAGA] Rollback: Varijanta {variant.id} obrisana.")
            
            if price_list_id_for_rollback:
                print(f"[SAGA] Potrebno je obrisati PriceList sa ID: {price_list_id_for_rollback}")
            
            raise RuntimeError(f"Saga neuspješna: {e}")
