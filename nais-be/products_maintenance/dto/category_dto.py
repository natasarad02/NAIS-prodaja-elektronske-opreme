from pydantic import BaseModel, ConfigDict
from typing import Optional, List
from uuid import UUID

class CategoryDTO(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    id: Optional[UUID]
    name: str
    description: str
    parent_id: Optional[UUID] = None
    status: str
    children: Optional[List[UUID]] = []


  
