from fastapi import APIRouter, HTTPException
from dto.variant_dto import VariantDTO
from service.variant_service import VariantService
from fastapi import Query
from saga.create_variant_orchestrator import VariantSagaOrchestrator

router = APIRouter(prefix="/variants", tags=["Variants"])

@router.post("/", response_model=VariantDTO)
def create_variant(variant: VariantDTO):
    return VariantService.create_variant(variant)

@router.get("/{variant_id}", response_model=VariantDTO)
def get_variant(variant_id: str):
    variant = VariantService.get_variant(variant_id)
    if not variant:
        raise HTTPException(status_code=404, detail="Variant not found")
    return variant

@router.put("/{variant_id}", response_model=VariantDTO)
def update_variant(variant_id: str, variant: VariantDTO):
    updated = VariantService.update_variant(variant.product_id, variant_id, variant)
    if not updated:
        raise HTTPException(status_code=404, detail="Variant not found")
    return updated

@router.delete("/{variant_id}")
def delete_variant(variant_id: str, product_id: str = Query(...)):
    success = VariantService.delete_variant(product_id, variant_id)
    if not success:
        raise HTTPException(status_code=404, detail="Variant not found")
    return {"detail": "Variant deleted"}

@router.get("/")
def get_all_variants():
    return VariantService.get_all_variants()


@router.post("/variants/create-with-price", response_model=VariantDTO)
def create_variant_with_price(variant_data: VariantDTO, initial_price: float):
    try:
        result = VariantSagaOrchestrator.create_variant_with_price(variant_data, initial_price)
        return result["variant"]
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
