from service.variant_service import VariantService
from dto.variant_dto import VariantDTO
from typing import Optional
from uuid import UUID
from clients.price_list_client import PriceListClient
from datetime import datetime, timedelta
class VariantSagaOrchestrator:
    price_list_client = PriceListClient(base_url="http://localhost:8080/api/price_list")

    @staticmethod
    def create_variant_with_price(variant_data: VariantDTO, initial_price: Optional[float] = None):
        
        variant = None

        try:
            variant = VariantService.create_variant(variant_data)

            if not variant:
                raise RuntimeError("Variant creation failed - aborting transaction")
            
            price_list_data = {
                "title": f"Initial price list for variant {variant.id}",
                "discount": 0,
                "quantity": 1,
                "start_date": datetime.now(),
                "expire_date": datetime.now() + timedelta(days=30),
                "current_phase_id": None,
                "region_ids": [1],          
                "user_type_ids": [1],       
                "items": [{
                    "productId": variant.id,
                    "price": initial_price or 0
                }]
            }

            price_list = VariantSagaOrchestrator.price_list_client.create_price_list(**price_list_data)
            return {"variant": variant, "priceList": price_list}
        except Exception as e:
            if variant:
                try:
                    VariantService.delete_variant(variant.id)
                except Exception as rollback_err:
                    raise RuntimeError(f"Rollback failed: {rollback_err}") from e
                
            raise RuntimeError(f"Saga transaction failed: {e}")


