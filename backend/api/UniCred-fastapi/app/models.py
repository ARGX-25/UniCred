from sqlalchemy import Column, Integer, String, DateTime, Enum, ForeignKey, Date
from sqlalchemy.sql import func

from app.database import Base
from app.core.constants import UniversityStatus,UserRole, UserStatus, CredentialStatus, VerificationResult


class University(Base):
    __tablename__ = "universities"

    id = Column(Integer, primary_key=True, index=True)

    name = Column(String, nullable=False, unique=True)
    domain = Column(String, nullable=False, unique=True)

    status = Column(
        Enum(UniversityStatus),
        nullable=False,
        default=UniversityStatus.ACTIVE
    )

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )


class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)

    full_name = Column(String, nullable=False)

    email = Column(String, nullable=False, unique=True, index=True)
    valid_id = Column(String, nullable=False, unique=True)

    password_hash = Column(String, nullable=False)

    role = Column(
        Enum(UserRole),
        nullable=False
    )

    status = Column(
        Enum(UserStatus),
        nullable=False,
        default=UserStatus.ACTIVE
    )

    # Only set for UNIVERSITY_ADMIN users
    university_id = Column(
        Integer,
        ForeignKey("universities.id", ondelete="SET NULL"),
        nullable=True
    )

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )


class Student(Base):
    __tablename__ = "students"

    id = Column(Integer, primary_key=True, index=True)

    user_id = Column(
        Integer,
        ForeignKey("users.id", ondelete="CASCADE"),
        nullable=False,
        unique=True
    )

    university_id = Column(
        Integer,
        ForeignKey("universities.id", ondelete="CASCADE"),
        nullable=False
    )

    student_id = Column(String, nullable=False, index=True)
    program = Column(String, nullable=False)

    phone_number = Column(String, nullable=False)
    address = Column(String, nullable=False)

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )


class Credential(Base):
    __tablename__ = "credentials"

    id = Column(Integer, primary_key=True, index=True)

    title = Column(String, nullable=False)
    credential_type = Column(String, nullable=False)

    student_id = Column(
        Integer,
        ForeignKey("students.id", ondelete="CASCADE"),
        nullable=False
    )

    university_id = Column(
        Integer,
        ForeignKey("universities.id", ondelete="CASCADE"),
        nullable=False
    )

    hash = Column(String, nullable=False, unique=True)

    graduation_date = Column(Date, nullable=False)

    status = Column(
        Enum(CredentialStatus),
        nullable=False,
        default=CredentialStatus.ISSUED
    )

    issued_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )

    revoked_at = Column(DateTime(timezone=True), nullable=True)

class Verification(Base):
    __tablename__ = "verifications"

    id = Column(Integer, primary_key=True, index=True)

    credential_id = Column(
        Integer,
        ForeignKey("credentials.id", ondelete="CASCADE"),
        nullable=False
    )

    recruiter_user_id = Column(
        Integer,
        ForeignKey("users.id", ondelete="CASCADE"),
        nullable=False
    )

    result = Column(
        Enum(VerificationResult),
        nullable=False
    )

    verified_at = Column(
        DateTime(timezone=True),
        server_default=func.now()
    )