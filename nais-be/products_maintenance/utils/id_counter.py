from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class IdCounter(Model):
    __keyspace__ = 'product_portfolio'
    table_name = 'id_counter'

    entity_name = columns.Text(primary_key=True)
    last_id = columns.BigInt(default=99) 