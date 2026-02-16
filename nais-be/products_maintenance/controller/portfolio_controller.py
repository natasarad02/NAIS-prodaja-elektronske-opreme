from fastapi import APIRouter, Query
from service.portfolio_service import PortfolioService

router = APIRouter(prefix="/portfolio", tags=["Portfolio"])

@router.get("/products/count-by-category")
def products_count_by_category():
    return PortfolioService.count_products_per_category()

@router.get("/products/by-category/{category_id}")
def products_by_category(category_id: str):
    return PortfolioService.get_products_by_category(category_id)

@router.get("/variants/count-by-product")
def variants_count_by_product():
    return PortfolioService.count_variants_per_product()

@router.get("/variants/by-product/{product_id}")
def variants_by_product(product_id: str):
    return PortfolioService.get_variants_by_product(product_id)

@router.get("/product-history/count-updates")
def updates_count_per_product():
    return PortfolioService.count_updates_per_product()
