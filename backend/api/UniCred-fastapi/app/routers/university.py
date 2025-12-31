import hashlib

from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session

from app.database import get_db
from app.models import User, Student, Credential
from app.schemas import StudentCreate, StudentResponse, CredentialCreate, CredentialResponse
from app.core.constants import UserRole, CredentialStatus
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
        university_id=university_id,
        full_name = payload.full_name,
        valid_id = payload.valid_id
    )

    db.add(user)
    db.flush()
    # IMPORTANT: gives us user.id without committing

    # 3. Create Student
    student = Student(
        user_id=user.id,
        university_id=university_id,
        student_id=payload.student_id,
        program=payload.program,
        phone_number=payload.phone_number,
        address=payload.address,

    )

    db.add(student)

    # 4. Commit once
    db.commit()

    # 5. Refresh to load generated fields
    db.refresh(student)

    return student

@router.get("/students", response_model=list[StudentResponse])
def get_students(
    db: Session = Depends(get_db),
    # later: current_user = Depends(get_current_university_admin)
):
    university_id = 1  # temporary until auth is wired

    results = (
        db.query(
            Student.id,
            Student.user_id,
            Student.university_id,
            Student.student_id,
            Student.program,
            Student.phone_number,
            Student.address,
            Student.created_at,

            User.full_name,
            User.email,
            User.valid_id,
            User.status.label("user_status"),
        )
        .join(User, User.id == Student.user_id)
        .filter(Student.university_id == university_id)
        .order_by(Student.created_at.desc())
        .all()
    )

    return results

@router.get("/students/{student_id}")
def get_student(student_id: str):
    return {"student_id": student_id}

@router.post("/credentials", status_code=201)
def issue_credential(
    payload: CredentialCreate,
    db: Session = Depends(get_db),
    # later: current_user = Depends(get_current_university_admin)
):
    UNIVERSITY_ID = 1  # temporary, from auth later

    student = (
        db.query(Student)
        .filter(
            Student.student_id == payload.student_id,
            Student.university_id == UNIVERSITY_ID
        )
        .first()
    )

    if not student:
        raise HTTPException(status_code=404, detail="Student not found")


    raw_hash = f"{UNIVERSITY_ID}:{student.student_id}:{payload.credential_type}:{payload.graduation_date}"
    credential_hash = hashlib.sha256(raw_hash.encode()).hexdigest()

    existing = db.query(Credential).filter(
        Credential.hash == credential_hash
    ).first()

    if existing:
        raise HTTPException(
            status_code=409,
            detail="Credential already issued for this student"
        )

    credential = Credential(
        title=payload.title,
        credential_type=payload.credential_type,
        student_id=student.id,
        university_id=UNIVERSITY_ID,
        graduation_date=payload.graduation_date,
        hash=credential_hash,
        status=CredentialStatus.ISSUED
    )

    db.add(credential)
    db.commit()
    db.refresh(credential)

    return {
        "id": credential.id,
        "hash": credential.hash,
        "status": credential.status,
        "issued_at": credential.issued_at
    }


@router.get("/credentials", response_model=list[CredentialResponse])
def get_credentials(
    db: Session = Depends(get_db),
    # later: current_user = Depends(get_current_university_admin)
):
    university_id = 1  # temporary

    results = (
        db.query(
            Credential.id,
            Credential.title,
            Credential.credential_type,
            Credential.graduation_date,
            Credential.hash,
            Credential.status,
            Credential.issued_at,
            Credential.revoked_at,

            Student.student_id,
            User.full_name.label("student_name"),
        )
        .join(Student, Student.id == Credential.student_id)
        .join(User, User.id == Student.user_id)
        .filter(Credential.university_id == university_id)
        .order_by(Credential.issued_at.desc())
        .all()
    )

    return results


@router.post("/credentials/{credential_id}/revoke")
def revoke_credential(credential_id: str):
    return {"credential_id": credential_id, "status": "revoked"}
