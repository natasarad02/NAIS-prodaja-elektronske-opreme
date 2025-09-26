from uuid import uuid4
from entity.product import Product

class ProductRepository:

    @staticmethod
    def create_product(brand: str, name: str, description: str, category_id, phase_id):
        product = Product.create(
            category_id = category_id,
            phase_id = phase_id,
            id=uuid4(),
            brand = brand,
            name = name,
            description = description
        )
        return product
    
    @staticmethod
    def get_product_by_id(category_id, product_id):
        return Product.objects(category_id=category_id, id=product_id).first()

    @staticmethod
    def update_product(category_id, product_id, **kwargs):
        product = Product.objects(category_id=category_id, id=product_id).first()
        if not product:
            return None
        for key, value in kwargs.items():
            setattr(product, key, value)
        product.save()
        return product

    
    @staticmethod
    def delete_product(category_id, product_id):
        product = Product.objects(category_id=category_id, id=product_id).first()
        if product:
            product.delete()
            return True
        return False


    @staticmethod
    def get_products_by_category(category_id):
        return Product.objects(category_id=category_id)

    

    @staticmethod
    def get_all():
        return Product.objects.all()
    
    @staticmethod
    def count_products_per_category():
        return Product.objects.group_by("category_id").values("category_id").count()
    
    '''
    SELECT category_id, count(*) AS product_count FROM product GROUP BY category_id
    '''

    @staticmethod
    def get_products_by_category(category_id):
        return list(Product.objects(category_id=category_id))
    
    '''
    SELECT * FROM product WHERE category_id={category_id}
    '''
