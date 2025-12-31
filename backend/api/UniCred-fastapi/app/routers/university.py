
from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session

from app.database import get_db
from app.models import User, Student
from app.schemas import StudentCreate, StudentResponse
from app.core.constants import UserRole
from app.core.security import hash_password

router = APIRouter()

@router.post("/students", response_model=StudentResponse, status_code=status.HTTP_201_CREATED)
def create_student(
    payload: StudentCreate,
    db: Session = Depends(get_db)
):
    # TEMPORARY: hardcoded university context
    university_id = 1

    # 1. Check if user already exists
    existing_user = db.query(User).filter(User.email == payload.email).first()
    if existing_user:
        raise HTTPException(
            status_code=400,
            detail="User with this email already exists"
        )

    # 2. Create User
    user = User(
        email=payload.email,
        password_hash=hash_password(payload.password),
        role=UserRole.STUDENT,
        university_id=university_id
    )

    db.add(user)
    db.flush()
    # IMPORTANT: gives us user.id without committing

    # 3. Create Student
    student = Student(
        user_id=user.id,
        university_id=university_id,
        student_identifier=payload.student_identifier,
        program=payload.program
    )

    db.add(student)

    # 4. Commit once
    db.commit()

    # 5. Refresh to load generated fields
    db.refresh(student)

    return student

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
