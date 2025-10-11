import json
import requests
from datetime import datetime
from typing import Optional, Dict, Any

class PriceListClient:
    def __init__(self, base_url: str, timeout: int = 10):
        self.base_url = base_url.rstrip('/')
        self.session = requests.Session()
        self.timeout = timeout

    def create_price_list(self, title: str, discount: Optional[float], quantity: Optional[int],
                          start_date: datetime, expire_date: datetime,
                          current_phase_id: Optional[int],
                          region_ids: list[int], user_type_ids: list[int],
                          items: Optional[list[Dict[str, Any]]] = None) -> Dict[str, Any]:
     

        payload = {
            "title": title,
            "discount": discount,
            "quantity": quantity,
            "startDate": start_date.isoformat(),
            "expireDate": expire_date.isoformat(),
            "currentPhaseId": current_phase_id,
            "regionIds": region_ids,
            "userTypeIds": user_type_ids,
            "items": items or []
        }

        try:
            response = self.session.post(
                f"{self.base_url}",
                data=json.dumps(payload),
                headers={"Content-Type": "application/json"},
                timeout=self.timeout
            )
            response.raise_for_status()
        except requests.RequestException as e:
            raise RuntimeError(f"Failed to create price list: {e}") from e

        return response.json()
