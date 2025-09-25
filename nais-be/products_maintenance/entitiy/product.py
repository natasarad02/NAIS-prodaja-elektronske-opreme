from cassandra.cqlengine.models import Model
from cassandra.cqlengine import columns
from cassandra.cqlengine.management import sync_table
from .variant import Variant
from .category import Category
from .product_lifecycle_phase import ProductLifecyclePhase

class Product(Model):
    __keyspace__ = 'product_portfolio'

    id = columns.UUID(primary_key=True) 
    brand = columns.Text(required=True)
    name = columns.Text(required=True)
    description = columns.Text(required=True)
    category_id = columns.UUID(required=True)
    phase_id = columns.UUID(required=True)