from uuid import uuid4
from models.variant import Variant

class VariantRepository:

    @staticmethod
    def create_variant(variant_code: str, description: str, model_number: str, product_id):
        variant = Variant.create(
            id=uuid4(),
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
    def update_variant(variant_id, **kwargs):
        variant = Variant.objects(id=variant_id).first()
        if not variant:
            return None
        for key, value in kwargs.items():
            setattr(variant, key, value)
        variant.save()
        return variant

    @staticmethod
    def delete_variant(variant_id):
        variant = Variant.objects(id=variant_id).first()
        if variant:
            variant.delete()
            return True
        return False

    @staticmethod
    def get_variants_by_product(product_id):
        return Variant.objects(product_id=product_id)
