from repository.product_repository import ProductRepository
from repository.variant_repository import VariantRepository
from repository.product_history_repository import ProductHistoryRepository
from dto.product_dto import ProductDTO
from dto.variant_dto import VariantDTO
from dto.product_history_dto import ProductHistoryDTO

class PortfolioService:

    @staticmethod
    def count_products_per_category():
        rows = ProductRepository.count_products_per_category()
        return [{"category_id": r.category_id, "product_count": r.product_count} for r in rows]

    @staticmethod
    def get_products_by_category(category_id):
        products = ProductRepository.get_products_by_category(category_id)
        return [ProductDTO.model_validate(p) for p in products]

    @staticmethod
    def count_variants_per_product():
        rows = VariantRepository.count_variants_per_product()
        return [{"product_id": r.product_id, "variant_count": r.variant_count} for r in rows]

    @staticmethod
    def get_variants_by_product(product_id):
        variants = VariantRepository.get_variants_by_product(product_id)
        return [VariantDTO.model_validate(v) for v in variants]

    @staticmethod
    def count_updates_per_product():
        rows = ProductHistoryRepository.count_updates_per_product()
        return [{"product_id": r.product_id, "update_count": r.update_count} for r in rows]