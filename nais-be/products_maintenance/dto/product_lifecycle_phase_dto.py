from pydantic import BaseModel, ConfigDict
from uuid import UUID
from typing import Optional

class ProductLifecyclePhaseDTO(BaseModel):

    model_config = ConfigDict(from_attributes=True)
    id: Optional[int]
    name: str

