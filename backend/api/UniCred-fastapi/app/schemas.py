from pydantic import BaseModel, EmailStr
from typing import Optional
from datetime import datetime
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
    student_identifier: str
    program: str


class StudentResponse(BaseModel):
    id: int
    user_id: int
    university_id: int
    student_identifier: str
    program: str
    created_at: datetime

    class Config:
        from_attributes = True


# ---------- CREDENTIAL ----------

class CredentialCreate(BaseModel):
    title: str
    credential_type: str
    student_id: int


class CredentialResponse(BaseModel):
    id: int
    title: str
    credential_type: str
    student_id: int
    university_id: int
    hash: str
    status: CredentialStatus
    issued_at: datetime
    revoked_at: Optional[datetime]

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
