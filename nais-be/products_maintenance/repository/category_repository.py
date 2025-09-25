from uuid import uuid4
from entity.category import Category

class CategoryRepository:

    @staticmethod
    def create_category(name: str, description: str, parent_id=None, status="ACTIVE"):
        category = Category.create(
            id=uuid4(),
            name=name,
            description=description,
            parent_id=parent_id,
            status=status
        )
        return category

    @staticmethod
    def get_category_by_id(category_id):
        return Category.objects(id=category_id).first()
    
    @staticmethod
    def get_all():
        return Category.objects

    @staticmethod
    def update_category(category_id, **kwargs):
        category = Category.objects(id=category_id).first()
        if not category:
            return None
        for key, value in kwargs.items():
            setattr(category, key, value)
        category.save()
        return category

    @staticmethod
    def delete_category(category_id):
        category = Category.objects(id=category_id).first()
        if category:
            category.delete()
            return True
        return False

    @staticmethod
    def get_children(parent_id):
        return Category.objects(parent_id=parent_id)
