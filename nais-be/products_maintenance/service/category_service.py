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
        return CategoryDTO.model_validate(category)

    @staticmethod
    def get_category(category_id):
        category = CategoryRepository.get_category_by_id(category_id)
        if category:
            return CategoryDTO.model_validate(category)
        return None

    @staticmethod
    def update_category(parent_id, category_id, category_data: CategoryDTO):

        category_dict = category_data.model_dump(exclude_unset=True)
        category_dict.pop("parent_id", None)
        category = CategoryRepository.update_category(parent_id, category_id, **category_dict)
        if category:
            return CategoryDTO.model_validate(category)
        return None

    @staticmethod
    def delete_category(parent_id, category_id):
        return CategoryRepository.delete_category(parent_id, category_id)
    

    @staticmethod
    def get_all_categories():
        categories = CategoryRepository.get_all()
        return [CategoryDTO.model_validate(cat) for cat in categories]