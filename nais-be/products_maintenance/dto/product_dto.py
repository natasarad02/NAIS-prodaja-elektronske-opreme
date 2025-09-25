from pydantic import BaseModel
from typing import List, Optional
from uuid import UUID

class ProductDTO(BaseModel):
    id: Optional[UUID]
    brand: str
    name: str
    description: str
    category_id: UUID
    phase_id: UUID
    variants: Optional[List[str]] = [] 

    class Config:
        orm_mode = True
