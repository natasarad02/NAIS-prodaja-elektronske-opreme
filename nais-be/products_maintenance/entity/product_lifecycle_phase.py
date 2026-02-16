from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class ProductLifecyclePhase(Model):
    __keyspace__ = 'product_portfolio'
    
    id = columns.BigInt(primary_key=True)
    name = columns.Text(required=True)