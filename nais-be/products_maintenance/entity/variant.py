from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class Variant(Model):
    __keyspace__ = 'product_portfolio'
    
    id = columns.UUID(primary_key=True)
    variant_code = columns.Text(required=True)
    description = columns.Text(required=True)
    model_number = columns.Text(required=True)
    product_id = columns.UUID(required=True)