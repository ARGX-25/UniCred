from passlib.context import CryptContext

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")


def hash_password(password: str) -> str:
    # bcrypt limit: 72 bytes
    normalized = password.encode("utf-8")[:72]
    return pwd_context.hash(normalized)
