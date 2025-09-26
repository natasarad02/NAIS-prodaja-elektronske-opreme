from repository.variant_repository import VariantRepository
from dto.variant_dto import VariantDTO

class VariantService:

    @staticmethod
    def create_variant(variant_data: VariantDTO):
        variant = VariantRepository.create_variant(
            variant_code=variant_data.variant_code,
            description=variant_data.description,
            model_number=variant_data.model_number,
            product_id=variant_data.product_id
        )
        return VariantDTO.model_validate(variant)

    @staticmethod
    def get_variant(variant_id):
        variant = VariantRepository.get_variant_by_id(variant_id)
        if variant:
            return VariantDTO.model_validate(variant)
        return None

    @staticmethod
    def update_variant(variant_id, variant_data: VariantDTO):
        variant = VariantRepository.update_variant(variant_id, **variant_data.dimodel_dump(exclude_unset=True))
        if variant:
            return VariantDTO.model_validate(variant)
        return None

    @staticmethod
    def delete_variant(variant_id):
        return VariantRepository.delete_variant(variant_id)
    
    @staticmethod
    def get_all_variants():
        variants = VariantRepository.get_all()
        return [VariantDTO.model_validate(v) for v in variants]
