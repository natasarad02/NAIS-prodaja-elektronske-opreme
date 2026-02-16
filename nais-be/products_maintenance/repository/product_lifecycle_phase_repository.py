from uuid import uuid4
from entity.product_lifecycle_phase import ProductLifecyclePhase
from utils.get_next_id import get_next_id
class ProductLifecyclePhaseRepository:

    @staticmethod
    def create_phase(name: str):
        phase_id = get_next_id("product_lifecycle_phase")
        phase = ProductLifecyclePhase.create(
            id=phase_id,
            name=name
        )
        return phase

    @staticmethod
    def get_phase_by_id(phase_id):
        return ProductLifecyclePhase.objects(id=phase_id).first()

    @staticmethod
    def update_phase(phase_id, **kwargs):
        phase = ProductLifecyclePhase.objects(id=phase_id).first()
        if not phase:
            return None
        for key, value in kwargs.items():
            setattr(phase, key, value)
        phase.save()
        return phase

    @staticmethod
    def delete_phase(phase_id):
        phase = ProductLifecyclePhase.objects(id=phase_id).first()
        if phase:
            phase.delete()
            return True
        return False

    @staticmethod
    def get_all_phases():
        return ProductLifecyclePhase.objects()

    @staticmethod
    def get_all():
        return ProductLifecyclePhase.objects