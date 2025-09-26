from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns
import uuid

class Variant(Model):
    __keyspace__ = 'product_portfolio'
    
    product_id = columns.UUID(partition_key=True, required=True)
    id = columns.UUID(primary_key=True, default=uuid.uuid4, clustering_order="ASC")
    variant_code = columns.Text(required=True)
    description = columns.Text(required=True)
    model_number = columns.Text(required=True)