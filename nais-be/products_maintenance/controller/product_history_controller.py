from fastapi import APIRouter, HTTPException
from dto.product_history_dto import ProductHistoryDTO
from service.product_history_service import ProductHistoryService
from typing import List

router = APIRouter(prefix="/history", tags=["Product History"])

@router.post("/", response_model=ProductHistoryDTO)
def create_history(history: ProductHistoryDTO):
    return ProductHistoryService.create_history(history)

@router.get("/{history_id}", response_model=ProductHistoryDTO)
def get_history(history_id: str):
    history = ProductHistoryService.get_history(history_id)
    if not history:
        raise HTTPException(status_code=404, detail="History not found")
    return history

@router.get("/product/{product_id}", response_model=List[ProductHistoryDTO])
def get_history_by_product(product_id: str):
    return ProductHistoryService.get_history_by_product(product_id)

@router.delete("/{history_id}")
def delete_history(history_id: str):
    success = ProductHistoryService.delete_history(history_id)
    if not success:
        raise HTTPException(status_code=404, detail="History not found")
    return {"detail": "History deleted"}


@router.get("/")
def get_all_histories():
    return ProductHistoryService.get_all_histories()