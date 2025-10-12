from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class Category(Model):
    __keyspace__ = 'product_portfolio'
    
    parent_id = columns.BigInt(partition_key=True) 
    id = columns.BigInt(primary_key=True, clustering_order="ASC") 
    name = columns.Text(required=True)
    description = columns.Text(required=True)
    status = columns.Text(required=True)