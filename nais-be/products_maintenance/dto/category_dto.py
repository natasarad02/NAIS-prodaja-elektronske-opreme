from pydantic import BaseModel
from typing import Optional, List
from uuid import UUID

class CategoryDTO(BaseModel):
    id: Optional[UUID]
    name: str
    description: str
    parent_id: Optional[UUID] = None
    status: str
    children: Optional[List[UUID]] = []

    class Config:
        orm_mode = True
