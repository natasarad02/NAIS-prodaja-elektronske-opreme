from fastapi import APIRouter, HTTPException
from dto.product_dto import ProductDTO
from service.product_service import ProductService
from typing import List
from fastapi import Query
router = APIRouter(prefix="/products", tags=["Products"])

@router.post("/", response_model=ProductDTO)
def create_product(product: ProductDTO):
    return ProductService.create_product(product)

@router.get("/{product_id}", response_model=ProductDTO)
def get_product(product_id: str):
    product = ProductService.get_product(product_id)
    if not product:
        raise HTTPException(status_code=404, detail="Product not found")
    return product

@router.put("/{product_id}", response_model=ProductDTO)
def update_product(product_id: str, product: ProductDTO):
    updated = ProductService.update_product(product.category_id, product_id, product)
    if not updated:
        raise HTTPException(status_code=404, detail="Product not found")
    return updated

@router.delete("/{product_id}")
def delete_product(product_id: str, category_id: str = Query(...)):
    success = ProductService.delete_product(category_id, product_id)
    if not success:
        raise HTTPException(status_code=404, detail="Product not found")
    return {"detail": "Product deleted"}

@router.get("/")
def get_all_products():
    return ProductService.get_all_products()