from pydantic import BaseModel
from uuid import UUID
from typing import Optional

class ProductLifecyclePhaseDTO(BaseModel):
    id: Optional[UUID]
    name: str

    class Config:
        orm_mode = True
