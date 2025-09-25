from cassandra.cqlengine.models import Model
from cassandra.cqlengine.query import BatchQuery
from cassandra.cqlengine import connection
from uuid import uuid4
from models.product import Product


class ProductRepository:

    @staticmethod
    def create_product(brand: str, name: str, description: str, category_id, phase_id):
        product = Product.create(
            id=uuid4(),
            brand = brand,
            name = name,
            description = description,
            category_id = category_id,
            phase_id = phase_id
        )
        return product
    
    @staticmethod
    def get_product_by_id(product_id):
        return Product.objects(id=product_id).first()

    @staticmethod
    def update_product(product_id, **kwargs):
        product = Product.objects(id=product_id).first()

        if not product:
            return None
        for key, value in kwargs.items():
            setattr(product, key, value)
        
        product.save()
        return product
    
    @staticmethod
    def delete_product(product_id):
        product = Product.objects(id=product_id).first()
        if product:
            product.delete()
            return True
        return False

    @staticmethod
    def get_products_by_category(category_id):
        return Product.objects(category_id=category_id)