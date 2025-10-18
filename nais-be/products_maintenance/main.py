from fastapi import FastAPI
from datetime import datetime, timedelta

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
from portfolio_routes import create_portfolio_router
from fastapi.middleware.cors import CORSMiddleware

import os, asyncio
from nats.aio.client import Client as NATS

import os
import asyncio
from nats.aio.client import Client as NATS


app = FastAPI(title="Products And Maintenance Microservice")
print("[PRODUCT] Module imported:", __name__)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:3000"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

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

portfolio_router = create_portfolio_router(session)
app.include_router(portfolio_router)

@app.get("/")
def root():
    return {"message": "Products Maintenance Microservice is running"}




NATS_URL = os.getenv("NATS_URL", "nats://nats:4222")
nc = NATS()

@app.on_event("startup")
async def nats_startup():
    await nc.connect(servers=[NATS_URL])
    print(f"[PRODUCT] Connected to NATS: {NATS_URL}")

    async def on_hello_product(msg):
        text = msg.data.decode("utf-8", errors="ignore")
        print(f"[PRODUCT] Primljeno: {text}")
        reply = "Zdravo sales, primljeno: " + text
        await nc.publish("hello.sales", reply.encode("utf-8"))
        await nc.flush()

    await nc.subscribe("hello.product", cb=on_hello_product)
    await nc.flush()
    print("[PRODUCT] Subscribed on 'hello.product'")

@app.on_event("shutdown")
async def nats_shutdown():
    try:
        await nc.drain()
    except Exception:
        pass
