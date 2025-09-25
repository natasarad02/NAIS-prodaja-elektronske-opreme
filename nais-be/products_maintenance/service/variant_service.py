from repositories.variant_repository import VariantRepository
from dtos.variant_dto import VariantDTO

class VariantService:

    @staticmethod
    def create_variant(variant_data: VariantDTO):
        variant = VariantRepository.create_variant(
            variant_code=variant_data.variant_code,
            description=variant_data.description,
            model_number=variant_data.model_number,
            product_id=variant_data.product_id
        )
        return VariantDTO.from_orm(variant)

    @staticmethod
    def get_variant(variant_id):
        variant = VariantRepository.get_variant_by_id(variant_id)
        if variant:
            return VariantDTO.from_orm(variant)
        return None

    @staticmethod
    def update_variant(variant_id, variant_data: VariantDTO):
        variant = VariantRepository.update_variant(variant_id, **variant_data.dict(exclude_unset=True))
        if variant:
            return VariantDTO.from_orm(variant)
        return None

    @staticmethod
    def delete_variant(variant_id):
        return VariantRepository.delete_variant(variant_id)