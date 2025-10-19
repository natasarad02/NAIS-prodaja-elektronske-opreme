from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class Variant(Model):
    __keyspace__ = 'product_portfolio'
    
    product_id = columns.BigInt(partition_key=True, required=True)
    id = columns.BigInt(primary_key=True, clustering_order="ASC")
    variant_code = columns.Text(required=True)
    description = columns.Text(required=True)
    model_number = columns.Text(required=True)