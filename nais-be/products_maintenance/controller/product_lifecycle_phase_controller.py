from fastapi import APIRouter, HTTPException
from dto.product_lifecycle_phase_dto import ProductLifecyclePhaseDTO
from service.product_lifecycle_phase_service import ProductLifecyclePhaseService
from typing import List

router = APIRouter(prefix="/phases", tags=["Product Lifecycle Phases"])

@router.post("/", response_model=ProductLifecyclePhaseDTO)
def create_phase(phase: ProductLifecyclePhaseDTO):
    return ProductLifecyclePhaseService.create_phase(phase)

@router.get("/{phase_id}", response_model=ProductLifecyclePhaseDTO)
def get_phase(phase_id: str):
    phase = ProductLifecyclePhaseService.get_phase(phase_id)
    if not phase:
        raise HTTPException(status_code=404, detail="Phase not found")
    return phase

@router.put("/{phase_id}", response_model=ProductLifecyclePhaseDTO)
def update_phase(phase_id: str, phase: ProductLifecyclePhaseDTO):
    updated = ProductLifecyclePhaseService.update_phase(phase_id, phase)
    if not updated:
        raise HTTPException(status_code=404, detail="Phase not found")
    return updated

@router.delete("/{phase_id}")
def delete_phase(phase_id: str):
    success = ProductLifecyclePhaseService.delete_phase(phase_id)
    if not success:
        raise HTTPException(status_code=404, detail="Phase not found")
    return {"detail": "Phase deleted"}

@router.get("/", response_model=List[ProductLifecyclePhaseDTO])
def get_all_phases():
    return ProductLifecyclePhaseService.get_all_phases()


@router.get("/")
def get_all_phases():
    return ProductLifecyclePhaseService.get_all_phases()