from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns
import uuid

class Product(Model):
    __keyspace__ = 'product_portfolio'
    category_id = columns.BigInt(partition_key=True)
    phase_id = columns.BigInt(primary_key=True, clustering_order="ASC")
    id = columns.BigInt(primary_key=True, default=uuid.uuid4) 
    brand = columns.Text(required=True)
    name = columns.Text(required=True)
    description = columns.Text(required=True)