# test_veze.py
import os
import asyncio
from nats.aio.client import Client as NATS
from nats.errors import TimeoutError, NoRespondersError

NATS_URL = os.getenv("NATS_URL", "nats://localhost:4222")
PORUKA_ZA_SLANJE = "Jednostavan test signal iz Python skripte."
SUBJECT = "saga.variant.create"   # promeni po potrebi

async def pokreni_test():
    nc = NATS()
    try:
        print(f"[PRODUCT] Povezujem se na NATS: {NATS_URL}")
        await nc.connect(servers=[NATS_URL])
        print("[PRODUCT] Konekcija uspešna.")

        print(f"[PRODUCT] Šaljem request na subject '{SUBJECT}': '{PORUKA_ZA_SLANJE}'")
        try:
            msg = await nc.request(SUBJECT, PORUKA_ZA_SLANJE.encode("utf-8"), timeout=10)
            print(f"[PRODUCT] Primljen odgovor: '{msg.data.decode()}'")
            print("\n>>> SJAJNO! Veza između servisa je USPOSTAVLJENA! <<<\n")
        except NoRespondersError:
            print(f"\nXXX Nema respondera na subject '{SUBJECT}'. "
                  f"Treba da ti sales_service (ili drugi servis) sluša i odgovara. XXX\n")
        except TimeoutError:
            print(f"\nXXX Isteklo vreme čekanja na odgovor sa subject-a '{SUBJECT}'. XXX\n")

    except Exception as e:
        print(f"\nXXX GREŠKA! Nije uspelo: {e} XXX\n")
    finally:
        if nc.is_connected:
            await nc.close()
            print("[PRODUCT] Konekcija zatvorena.")

if __name__ == "__main__":
    asyncio.run(pokreni_test())
