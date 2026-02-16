from pydantic import BaseModel, ConfigDict
from uuid import UUID
from typing import Optional

class VariantDTO(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    id: Optional[int]
    variant_code: str
    description: str
    model_number: str
    product_id: int
