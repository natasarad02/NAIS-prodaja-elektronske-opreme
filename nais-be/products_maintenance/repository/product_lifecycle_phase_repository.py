from uuid import uuid4
from entity.product_lifecycle_phase import ProductLifecyclePhase

class ProductLifecyclePhaseRepository:

    @staticmethod
    def create_phase(name: str):
        phase = ProductLifecyclePhase.create(
            id=uuid4(),
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