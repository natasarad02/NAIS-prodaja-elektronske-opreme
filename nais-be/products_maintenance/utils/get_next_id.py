from cassandra.cqlengine.query import DoesNotExist
from .id_counter import IdCounter

def get_next_id(entity_name: str) -> int:
    try:
        counter = IdCounter.objects.get(entity_name=entity_name)
        counter.last_id += 1
        counter.save()
    except DoesNotExist:
        counter = IdCounter.create(entity_name=entity_name, last_id=100)
    return counter.last_id
