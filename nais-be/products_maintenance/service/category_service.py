from repository.category_repository import CategoryRepository
from dto.category_dto import CategoryDTO

class CategoryService:

    @staticmethod
    def create_category(category_data: CategoryDTO):
        category = CategoryRepository.create_category(
            name=category_data.name,
            description=category_data.description,
            parent_id=category_data.parent_id,
            status=category_data.status
        )
        return CategoryDTO.from_orm(category)

    @staticmethod
    def get_category(category_id):
        category = CategoryRepository.get_category_by_id(category_id)
        if category:
            return CategoryDTO.from_orm(category)
        return None

    @staticmethod
    def update_category(category_id, category_data: CategoryDTO):
        category = CategoryRepository.update_category(category_id, **category_data.dict(exclude_unset=True))
        if category:
            return CategoryDTO.from_orm(category)
        return None

    @staticmethod
    def delete_category(category_id):
        return CategoryRepository.delete_category(category_id)
    

    @staticmethod
    def get_all_categories():
        return CategoryRepository.get_all()