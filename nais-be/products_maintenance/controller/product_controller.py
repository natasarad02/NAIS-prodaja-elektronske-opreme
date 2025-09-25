from fastapi import APIRouter, HTTPException
from dtos.product_dto import ProductDTO
from services.product_service import ProductService
from typing import List