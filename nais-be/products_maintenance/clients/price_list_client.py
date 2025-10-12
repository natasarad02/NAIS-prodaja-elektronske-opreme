import json
import requests
from datetime import datetime
from typing import Optional, Dict, Any

class PriceListClient:
    def __init__(self, base_url: str, timeout: int = 10):
        self.base_url = base_url
        self.session = requests.Session()
        self.timeout = timeout

    def create_price_list(self, title: str, discount: Optional[float], quantity: Optional[int],
                          startDate: str, expireDate: str,
                          currentPhaseId: Optional[int],
                          regionIds: list[int], userTypeIds: list[int],
                          items: Optional[list[Dict[str, Any]]] = None) -> Dict[str, Any]:
     

        payload = {
            "title": title,
            "discount": discount,
            "quantity": quantity,
            "startDate": startDate,
            "expireDate": expireDate,
            "currentPhaseId": currentPhaseId,
            "regionIds": regionIds,
            "userTypeIds": userTypeIds,
            "items": items
        }

        headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Host": "localhost:8081"  
    }

        try:
            print("Payload to send:", json.dumps(payload, indent=2))
            print("POST URL:", self.base_url)
      #      response = requests.get(
    #            "http://localhost:8081/api/price_list",
     #           headers={"Accept": "application/json"}
    #        )
        #    print(response.status_code, response.text)
            response = self.session.post(
                self.base_url,
                json=payload,
                headers=headers,
                timeout=self.timeout
            )
            print("Response status:", response.status_code)
            print("Response body:", response.text)
            response.raise_for_status()
        except requests.RequestException as e:
            raise RuntimeError(f"Failed to create price list: {e}") from e

        return response.json()
