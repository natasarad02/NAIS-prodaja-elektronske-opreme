from pydantic import BaseModel, ConfigDict
from typing import Optional, List
from uuid import UUID

class CategoryDTO(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    id: Optional[int]
    name: str
    description: str
    parent_id: Optional[int] = None
    status: str
    children: Optional[List[int]] = []


  
