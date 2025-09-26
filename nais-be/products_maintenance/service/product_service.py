from repository.product_repository import ProductRepository
from dto.product_dto import ProductDTO

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

        return ProductDTO.model_validate(product)
    

    @staticmethod
    def get_product(product_id):
        product = ProductRepository.get_product_by_id(product_id)
        if product:
            return ProductDTO.model_validate(product)
        return None

    @staticmethod
    def update_product(category_id, product_id, product_data: ProductDTO):

        product_dict = product_data.model_dump(exclude_unset=True)
        product_dict.pop("category_id", None)
        product = ProductRepository.update_product(category_id, product_id, **product_dict)
        if product:
            return ProductDTO.model_validate(product)
        return None

    @staticmethod
    def delete_product(category_id, product_id):
        return ProductRepository.delete_product(category_id, product_id)
    
    @staticmethod
    def get_all_products():
        products = ProductRepository.get_all()
        return [ProductDTO.model_validate(p) for p in products]