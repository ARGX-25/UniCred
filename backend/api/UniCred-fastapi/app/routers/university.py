# app/routers/university.py
from fastapi import APIRouter

router = APIRouter()

@router.post("/students")
def create_student():
    return {"message": "create student"}

@router.get("/students")
def list_students():
    return {"message": "list students"}

@router.get("/students/{student_id}")
def get_student(student_id: str):
    return {"student_id": student_id}

@router.post("/credentials")
def issue_credential():
    return {"message": "issue credential"}

@router.get("/credentials")
def list_credentials():
    return {"message": "list issued credentials"}

@router.post("/credentials/{credential_id}/revoke")
def revoke_credential(credential_id: str):
    return {"credential_id": credential_id, "status": "revoked"}
