
from fastapi import FastAPI

from app.routers import auth, student, university, recruiter

app = FastAPI(title="UniCred API")

app.include_router(auth.router, prefix="/auth", tags=["auth"])
app.include_router(student.router, prefix="/students", tags=["students"])
app.include_router(university.router, prefix="/university", tags=["university"])
app.include_router(recruiter.router, prefix="/recruiter", tags=["recruiter"])



"""
function restart-fastapi {
    taskkill /F /IM uvicorn.exe 2>$null
    taskkill /F /IM python.exe 2>$null
    uvicorn app.main:app --reload
}

"""