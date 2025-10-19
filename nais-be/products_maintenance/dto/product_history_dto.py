# dtos/product_history_dto.py
from pydantic import BaseModel, ConfigDict
from uuid import UUID
from datetime import datetime
from typing import Optional

class ProductHistoryDTO(BaseModel):

    model_config = ConfigDict(from_attributes=True)
    id: Optional[int]
    product_id: int
    old_brand: str
    old_name: str
    new_brand: str
    new_name: str
    old_description: str
    new_description: str
    old_category_id: int
    new_category_id: int
    old_phase_id: int
    new_phase_id: int
    update_timestamp: datetime
    user_id: int

