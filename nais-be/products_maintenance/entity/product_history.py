from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns
from datetime import datetime
import uuid

class ProductHistory(Model):
    __keyspace__ = 'product_portfolio'
    
    product_id = columns.UUID(partition_key=True, required=True)
    new_category_id = columns.UUID(primary_key=True, clustering_order="ASC")
    id = columns.UUID(primary_key=True, default=uuid.uuid4, clustering_order="ASC")  
    old_brand = columns.Text(required=True)
    old_name = columns.Text(required=True)
    new_brand = columns.Text(required=True)
    new_name = columns.Text(required=True)
    old_description = columns.Text(required=True)
    new_description = columns.Text(required=True)
    old_category_id = columns.UUID()
    old_phase_id = columns.UUID()
    new_phase_id = columns.UUID()
    update_timestamp = columns.DateTime(default=datetime.utcnow)
    user_id = columns.UUID(required=True)