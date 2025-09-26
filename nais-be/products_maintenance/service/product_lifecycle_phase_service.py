from repository.product_lifecycle_phase_repository import ProductLifecyclePhaseRepository
from dto.product_lifecycle_phase_dto import ProductLifecyclePhaseDTO

class ProductLifecyclePhaseService:

    @staticmethod
    def create_phase(phase_data: ProductLifecyclePhaseDTO):
        phase = ProductLifecyclePhaseRepository.create_phase(name=phase_data.name)
        return ProductLifecyclePhaseDTO.model_validate(phase)

    @staticmethod
    def get_phase(phase_id):
        phase = ProductLifecyclePhaseRepository.get_phase_by_id(phase_id)
        if phase:
            return ProductLifecyclePhaseDTO.model_validate(phase)
        return None

    @staticmethod
    def update_phase(phase_id, phase_data: ProductLifecyclePhaseDTO):
        phase = ProductLifecyclePhaseRepository.update_phase(phase_id, **phase_data.model_dump(exclude_unset=True))
        if phase:
            return ProductLifecyclePhaseDTO.model_validate(phase)
        return None

    @staticmethod
    def delete_phase(phase_id):
        return ProductLifecyclePhaseRepository.delete_phase(phase_id)

    @staticmethod
    def get_all_phases():
        return [ProductLifecyclePhaseDTO.model_validate(p) for p in ProductLifecyclePhaseRepository.get_all_phases()]
    

    @staticmethod
    def get_all_phases():
        phases = ProductLifecyclePhaseRepository.get_all()
        return [ProductLifecyclePhaseDTO.model_validate(p) for p in phases]