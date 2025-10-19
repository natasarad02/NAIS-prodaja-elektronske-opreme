import os
from fastapi import FastAPI
from nats.aio.client import Client as NATS

nc = NATS()

async def connect_nats(app: FastAPI):
    nats_url = os.getenv("NATS_URL", "nats://nats:4222")
    await nc.connect(servers=[nats_url])
    app.state.nats_conn = nc
    print(f"[NATS CLIENT] Uspešno povezan na {nats_url}")

async def disconnect_nats():
    if nc.is_connected:
        await nc.close()
        print("[NATS CLIENT] Konekcija zatvorena.")
