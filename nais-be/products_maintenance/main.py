from fastapi import FastAPI
from controller import (
    product_router,
    category_router,
    variant_router,
    phase_router,
    history_router
)
from cassandra.cqlengine import connection
from entity import sync_all_tables
import time
from cassandra.cluster import Cluster

app = FastAPI(title="Products And Maintenance Microservice")

app.include_router(product_router)
app.include_router(category_router)
app.include_router(variant_router)
app.include_router(phase_router)
app.include_router(history_router)
import load_cassandra_data as load_cassandra_data

CASSANDRA_HOST = "cassandra"
KEYSPACE = "product_portfolio"

while True:
    try:
        cluster = Cluster([CASSANDRA_HOST])
        session = cluster.connect()
        break
    except Exception as e:
        print("Waiting for Cassandra to start...", e)
        time.sleep(5)

session.execute(f"""
    CREATE KEYSPACE IF NOT EXISTS {KEYSPACE}
    WITH replication = {{'class': 'SimpleStrategy', 'replication_factor': 1}}
""")
session.set_keyspace(KEYSPACE)

connection.set_session(session)

tables_to_drop = ["product", "category", "variant", "product_lifecycle_phase", "product_history"]

for table in tables_to_drop:
    try:
        session.execute(f"DROP TABLE IF EXISTS {table}")
        print(f"Dropped table: {table}")
    except Exception as e:
        print(f"Failed to drop table {table}: {e}")

sync_all_tables()


load_cassandra_data.load_data(session, sql_file="cassandra_data.sql")


@app.get("/")
def root():
    return {"message": "Products Maintenance Microservice is running"}


@app.get("/portfolio/products/count-by-category")
def count_products_per_category():
    rows = session.execute("""
        SELECT category_id, count(*) AS product_count
        FROM product
        GROUP BY category_id
    """)
    return [dict(row) for row in rows]

@app.get("/portfolio/products/by-category/{category_id}")
def get_products_by_category(category_id: str):
    rows = session.execute(f"""
        SELECT * FROM product WHERE category_id={category_id}
    """)
    return [dict(row) for row in rows]

@app.get("/portfolio/variants/count-by-product")
def count_variants_per_product():
    rows = session.execute("""
        SELECT product_id, count(*) AS variant_count
        FROM variant
        GROUP BY product_id
    """)
    return [dict(row) for row in rows]

@app.get("/portfolio/variants/by-product/{product_id}")
def get_variants_by_product(product_id: str):
    rows = session.execute(f"""
        SELECT * FROM variant WHERE product_id={product_id}
    """)
    return [dict(row) for row in rows]

@app.get("/portfolio/product-history/count-by-product")
def count_updates_per_product():
    rows = session.execute("""
        SELECT product_id, count(*) AS update_count
        FROM product_history
        GROUP BY product_id
    """)
    return [dict(row) for row in rows]