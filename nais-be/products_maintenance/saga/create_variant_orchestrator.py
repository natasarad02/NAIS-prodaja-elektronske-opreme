from service.variant_service import VariantService
from dto.variant_dto import VariantDTO
from typing import Optional
from uuid import UUID

class VariantSagaOrchestrator:

    @staticmethod
    def create_variant_and_price(variant_data: VariantDTO, initial_price: Optional[float] = None):
        
        variant = None

        try:
            variant = VariantService.create_variant(variant_data)

            if not variant:
                raise RuntimeError("Variant creation failed - aborting transaction")
            
                #creating price

                return variant
        except Exception as e:
            if variant:
                try:
                    VariantService.delete_variant(variant.id)
                except Exception as rollback_err:
                    raise RuntimeError(f"Rollback failed: {rollback_err}") from e
                
            raise RuntimeError(f"Saga transaction failed: {e}")


