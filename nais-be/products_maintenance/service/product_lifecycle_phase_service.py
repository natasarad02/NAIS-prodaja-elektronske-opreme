from repositories.product_lifecycle_phase_repository import ProductLifecyclePhaseRepository
from dtos.product_lifecycle_phase_dto import ProductLifecyclePhaseDTO

class ProductLifecyclePhaseService:

    @staticmethod
    def create_phase(phase_data: ProductLifecyclePhaseDTO):
        phase = ProductLifecyclePhaseRepository.create_phase(name=phase_data.name)
        return ProductLifecyclePhaseDTO.from_orm(phase)

    @staticmethod
    def get_phase(phase_id):
        phase = ProductLifecyclePhaseRepository.get_phase_by_id(phase_id)
        if phase:
            return ProductLifecyclePhaseDTO.from_orm(phase)
        return None

    @staticmethod
    def update_phase(phase_id, phase_data: ProductLifecyclePhaseDTO):
        phase = ProductLifecyclePhaseRepository.update_phase(phase_id, **phase_data.dict(exclude_unset=True))
        if phase:
            return ProductLifecyclePhaseDTO.from_orm(phase)
        return None

    @staticmethod
    def delete_phase(phase_id):
        return ProductLifecyclePhaseRepository.delete_phase(phase_id)

    @staticmethod
    def get_all_phases():
        return [ProductLifecyclePhaseDTO.from_orm(p) for p in ProductLifecyclePhaseRepository.get_all_phases()]