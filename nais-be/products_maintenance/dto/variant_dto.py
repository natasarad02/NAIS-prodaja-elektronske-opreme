from pydantic import BaseModel
from uuid import UUID
from typing import Optional

class VariantDTO(BaseModel):
    id: Optional[UUID]
    variant_code: str
    description: str
    model_number: str
    product_id: UUID

    class Config:
        orm_mode = True
