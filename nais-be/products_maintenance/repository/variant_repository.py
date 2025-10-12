from uuid import uuid4
from entity.variant import Variant
from utils.get_next_id import get_next_id

class VariantRepository:

    @staticmethod
    def create_variant(variant_code: str, description: str, model_number: str, product_id):
        variant_id = get_next_id("variant")
        variant = Variant.create(
            id=variant_id,
            variant_code=variant_code,
            description=description,
            model_number=model_number,
            product_id=product_id
        )
        return variant

    @staticmethod
    def get_variant_by_id(variant_id):
        return Variant.objects(id=variant_id).first()

    @staticmethod
    def update_variant(product_id, variant_id, **kwargs):
        variant = Variant.objects(product_id=product_id, id=variant_id).first()
        if not variant:
            return None
        for key, value in kwargs.items():
            setattr(variant, key, value)
        variant.save()
        return variant

    @staticmethod
    def delete_variant(product_id, variant_id):
        variant = Variant.objects(product_id=product_id, id=variant_id).first()
        if variant:
            variant.delete()
            return True
        return False

    @staticmethod
    def get_variants_by_product(product_id):
        return Variant.objects(product_id=product_id)
    '''
    SELECT * FROM variant WHERE product_id={product_id}
    '''

    @staticmethod
    def get_all():
        return Variant.objects
    

    @staticmethod
    def count_variants_per_product():
        return Variant.objects.group_by("product_id").values("variant_id").count()
    
    '''
    SELECT product_id, count(*) AS variant_count FROM variant GROUP BY product_id
    '''