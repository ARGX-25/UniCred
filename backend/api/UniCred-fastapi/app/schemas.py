from pydantic import BaseModel, EmailStr
from typing import Optional
from datetime import datetime, date
from app.core.constants import (
    UserRole,
    UserStatus,
    UniversityStatus,
    CredentialStatus,
    VerificationResult
)

# ---------- USER ----------

class UserCreate(BaseModel):
    email: EmailStr
    password: str
    role: UserRole
    university_id: Optional[int] = None


class UserResponse(BaseModel):
    id: int
    email: EmailStr
    role: UserRole
    status: UserStatus
    university_id: Optional[int]
    created_at: datetime

    class Config:
        from_attributes = True


# ---------- UNIVERSITY ----------

class UniversityCreate(BaseModel):
    name: str
    domain: str


class UniversityResponse(BaseModel):
    id: int
    name: str
    domain: str
    status: UniversityStatus
    created_at: datetime

    class Config:
        from_attributes = True


# ---------- STUDENT ----------

class StudentCreate(BaseModel):
    email: EmailStr
    password: str
    student_id: str
    program: str
    full_name: str
    phone_number: str
    address: str
    valid_id: str


class StudentResponse(BaseModel):
    id: int
    user_id: int
    university_id: int

    full_name: str
    email: str
    valid_id: str

    student_id: str
    program: str
    phone_number: str
    address: str

    user_status: str
    created_at: datetime

    class Config:
        from_attributes = True


# ---------- CREDENTIAL ----------

class CredentialCreate(BaseModel):
    title: str
    credential_type: str
    student_id: str
    graduation_date: date


class CredentialResponse(BaseModel):
    id: int
    title: str
    credential_type: str
    graduation_date: date

    student_id: str
    student_name: str

    hash: str
    status: str
    issued_at: datetime
    revoked_at: datetime | None

    class Config:
        from_attributes = True

# ---------- VERIFICATION ----------

class VerificationResponse(BaseModel):
    id: int
    credential_id: int
    recruiter_user_id: int
    result: VerificationResult
    verified_at: datetime

    class Config:
        from_attributes = True
