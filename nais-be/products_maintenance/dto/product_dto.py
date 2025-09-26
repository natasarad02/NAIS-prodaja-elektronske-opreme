from pydantic import BaseModel, ConfigDict
from typing import List, Optional
from uuid import UUID

class ProductDTO(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    id: Optional[UUID]
    brand: str
    name: str
    description: str
    category_id: UUID
    phase_id: UUID
    variants: Optional[List[str]] = [] 

 