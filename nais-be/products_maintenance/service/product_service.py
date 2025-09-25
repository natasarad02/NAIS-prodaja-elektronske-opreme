from repositories.product_repository import ProductRepository
from dtos.product_dto import ProductDTO

class ProductService:

    @staticmethod
    def create_product(product_data: ProductDTO):
        product = ProductRepository.create_product(
            brand = product_data.brand,
            name = product_data.name,
            description = product_data.description,
            category_id = product_data.category_id,
            phase_id = product_data.phase_id
        )

        return ProductDTO.from_orm(product)
    

    @staticmethod
    def get_product(product_id):
        product = ProductRepository.get_product_by_id(product_id)
        if product:
            return ProductDTO.from_orm(product)
        return None

    @staticmethod
    def update_product(product_id, product_data: ProductDTO):
        product = ProductRepository.update_product(product_id, **product_data.dict(exclude_unset=True))
        if product:
            return ProductDTO.from_orm(product)
        return None

    @staticmethod
    def delete_product(product_id):
        return ProductRepository.delete_product(product_id)
    