from pydantic import BaseModel, ConfigDict
from typing import List, Optional
from uuid import UUID

class ProductDTO(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    id: Optional[int]
    brand: str
    name: str
    description: str
    category_id: int
    phase_id: int
    variants: Optional[List[int]] = [] 

 