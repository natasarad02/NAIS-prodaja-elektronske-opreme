from fastapi import APIRouter, HTTPException
from dto.category_dto import CategoryDTO
from service.category_service import CategoryService
from fastapi import Query
router = APIRouter(prefix="/categories", tags=["Categories"])

@router.post("/", response_model=CategoryDTO)
def create_category(category: CategoryDTO):
    return CategoryService.create_category(category)

@router.get("/{category_id}", response_model=CategoryDTO)
def get_category(category_id: str):
    category = CategoryService.get_category(category_id)
    if not category:
        raise HTTPException(status_code=404, detail="Category not found")
    return category

@router.put("/{category_id}", response_model=CategoryDTO)
def update_category(category_id: str, category: CategoryDTO):
    updated = CategoryService.update_category(category.parent_id, category_id, category)
    if not updated:
        raise HTTPException(status_code=404, detail="Category not found")
    return updated

@router.delete("/{category_id}")
def delete_category(category_id: str, parent_id: str = Query(...)):
    success = CategoryService.delete_category(parent_id, category_id)
    if not success:
        raise HTTPException(status_code=404, detail="Category not found")
    return {"detail": "Category deleted"}


@router.get("/")
def get_all_categories():
    return CategoryService.get_all_categories()
