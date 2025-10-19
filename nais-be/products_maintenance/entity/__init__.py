from .product import Product
from .category import Category
from .variant import Variant
from .product_lifecycle_phase import ProductLifecyclePhase
from .product_history import ProductHistory

def sync_all_tables():
    from cassandra.cqlengine.management import sync_table
    for model in [Product, Category, Variant, ProductLifecyclePhase, ProductHistory]:
        sync_table(model)