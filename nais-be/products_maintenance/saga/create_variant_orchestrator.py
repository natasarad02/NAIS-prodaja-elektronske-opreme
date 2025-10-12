from service.variant_service import VariantService
from dto.variant_dto import VariantDTO
from typing import Optional
from uuid import UUID
from clients.price_list_client import PriceListClient
from datetime import datetime, timedelta
from decimal import Decimal
import os 

class VariantSagaOrchestrator:
    
    price_list_base_url = os.environ.get(
        "SALES_BASE_URL", "http://sales_service:8081"
    ).rstrip("/") + "/api/price_list"

    price_list_client = PriceListClient(base_url=price_list_base_url)
        
    @staticmethod
    def create_variant_with_price(variant_data: VariantDTO, initial_price: Optional[float] = None):
        
        variant = None

        try:
            variant = VariantService.create_variant(variant_data)

            if not variant:
                raise RuntimeError("Variant creation failed - aborting transaction")
            
            today = datetime.now().date() 
            expire = today + timedelta(days=30)

            print("VARIJANTA " + str(variant.id))
            price_list_data = {
                "title": "Initial price list for variant",
                "discount": 0.0,
                "quantity": 1,
                "startDate": today.isoformat(),
                "expireDate": expire.isoformat(),
                "currentPhaseId": 1,      
                "regionIds": [],
                "userTypeIds": [],
                "items": [{
                    "productId": variant.id,
                    "price": initial_price
                }]
            }

            price_list = VariantSagaOrchestrator.price_list_client.create_price_list(**price_list_data)
            return {"variant": variant, "priceList": price_list}
        except Exception as e:
            if variant:
                try:
                    VariantService.delete_variant(variant.product_id, variant.id)
                except Exception as rollback_err:
                    raise RuntimeError(f"Rollback failed: {rollback_err}") from e
                
            raise RuntimeError(f"Saga transaction failed: {e}")


