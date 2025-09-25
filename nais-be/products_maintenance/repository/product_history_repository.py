from uuid import uuid4
from entity.product_history import ProductHistory

class ProductHistoryRepository:

    @staticmethod
    def create_history(
        product_id, old_brand, old_name, new_brand, new_name,
        old_description, new_description, old_category_id, new_category_id,
        old_phase_id, new_phase_id, update_timestamp, user_id
    ):
        history = ProductHistory.create(
            id=uuid4(),
            product_id=product_id,
            old_brand=old_brand,
            old_name=old_name,
            new_brand=new_brand,
            new_name=new_name,
            old_description=old_description,
            new_description=new_description,
            old_category_id=old_category_id,
            new_category_id=new_category_id,
            old_phase_id=old_phase_id,
            new_phase_id=new_phase_id,
            update_timestamp=update_timestamp,
            user_id=user_id
        )
        return history

    @staticmethod
    def get_history_by_id(history_id):
        return ProductHistory.objects(id=history_id).first()

    @staticmethod
    def get_history_by_product(product_id):
        return ProductHistory.objects(product_id=product_id)

    @staticmethod
    def delete_history(history_id):
        history = ProductHistory.objects(id=history_id).first()
        if history:
            history.delete()
            return True
        return False
    
    @staticmethod
    def get_all():
        return ProductHistory.objects
    

    
