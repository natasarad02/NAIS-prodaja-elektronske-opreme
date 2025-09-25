from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns
from datetime import datetime

class ProductHistory(Model):
    __keyspace__ = 'product_portfolio'
    
    id = columns.UUID(primary_key=True)
    product_id = columns.UUID(required=True)
    old_brand = columns.Text(required=True)
    old_name = columns.Text(required=True)
    new_brand = columns.Text(required=True)
    new_name = columns.Text(required=True)
    old_description = columns.Text(required=True)
    new_description = columns.Text(required=True)
    old_category_id = columns.UUID()
    new_category_id = columns.UUID()
    old_phase_id = columns.UUID()
    new_phase_id = columns.UUID()
    update_timestamp = columns.DateTime(default=datetime.utcnow)
    user_id = columns.UUID(required=True)