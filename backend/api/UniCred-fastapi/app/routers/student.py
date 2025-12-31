# app/routers/student.py
from fastapi import APIRouter

router = APIRouter()

@router.get("/me")
def get_my_profile():
    return {"message": "student profile"}

@router.get("/me/credentials")
def get_my_credentials():
    return {"message": "student credentials"}
