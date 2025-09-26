from repository.product_history_repository import ProductHistoryRepository
from dto.product_history_dto import ProductHistoryDTO

class ProductHistoryService:

    @staticmethod
    def create_history(history_data: ProductHistoryDTO):
        history = ProductHistoryRepository.create_history(
            product_id=history_data.product_id,
            old_brand=history_data.old_brand,
            old_name=history_data.old_name,
            new_brand=history_data.new_brand,
            new_name=history_data.new_name,
            old_description=history_data.old_description,
            new_description=history_data.new_description,
            old_category_id=history_data.old_category_id,
            new_category_id=history_data.new_category_id,
            old_phase_id=history_data.old_phase_id,
            new_phase_id=history_data.new_phase_id,
            update_timestamp=history_data.update_timestamp,
            user_id=history_data.user_id
        )
        return ProductHistoryDTO.model_validate(history)

    @staticmethod
    def get_history(history_id):
        history = ProductHistoryRepository.get_history_by_id(history_id)
        if history:
            return ProductHistoryDTO.model_validate(history)
        return None

    @staticmethod
    def get_history_by_product(product_id):
        return [ProductHistoryDTO.model_validate(h) for h in ProductHistoryRepository.get_history_by_product(product_id)]

    @staticmethod
    def delete_history(history_id):
        return ProductHistoryRepository.delete_history(history_id)
    
    @staticmethod
    def get_all_histories():
        histories = ProductHistoryRepository.get_all()
        return [ProductHistoryDTO.model_validate(h) for h in histories]