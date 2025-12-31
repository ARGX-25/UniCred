from enum import Enum


class UserRole(str, Enum):
    STUDENT = "student"
    RECRUITER = "recruiter"
    UNIVERSITY_ADMIN = "university_admin"


class UserStatus(str, Enum):
    ACTIVE = "active"
    DISABLED = "disabled"


class UniversityStatus(str, Enum):
    ACTIVE = "active"
    SUSPENDED = "suspended"


class CredentialStatus(str, Enum):
    ISSUED = "issued"
    REVOKED = "revoked"

class VerificationResult(str, Enum):
    AUTHENTIC = "authentic"
    REVOKED = "revoked"
    INVALID = "invalid"
