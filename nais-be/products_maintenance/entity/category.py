from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns

class Category(Model):
    __keyspace__ = 'product_portfolio'
    
    id = columns.UUID(primary_key=True)
    name = columns.Text(required=True)
    description = columns.Text(required=True)
    parent_id = columns.UUID()  
    status = columns.Text(required=True)