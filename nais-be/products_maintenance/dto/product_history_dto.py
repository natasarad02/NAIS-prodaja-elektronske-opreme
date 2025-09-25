# dtos/product_history_dto.py
from pydantic import BaseModel
from uuid import UUID
from datetime import datetime
from typing import Optional

class ProductHistoryDTO(BaseModel):
    id: Optional[UUID]
    product_id: UUID
    old_brand: str
    old_name: str
    new_brand: str
    new_name: str
    old_description: str
    new_description: str
    old_category_id: UUID
    new_category_id: UUID
    old_phase_id: UUID
    new_phase_id: UUID
    update_timestamp: datetime
    user_id: UUID

    class Config:
        orm_mode = True
