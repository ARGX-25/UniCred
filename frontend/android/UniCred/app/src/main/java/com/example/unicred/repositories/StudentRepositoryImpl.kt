package com.example.unicred.repositories

import com.example.unicred.models.domain.Student
import com.example.unicred.models.domain.toDomain
import com.example.unicred.models.network.CreateStudentRequest
import com.example.unicred.models.network.api.StudentApi

class StudentRepositoryImpl(
    private val api: StudentApi
) : StudentRepository {

    override suspend fun getStudents(): List<Student> {
        return api
            .getStudents()
            .map { it.toDomain() }
    }

    override suspend fun createStudent(
        request: CreateStudentRequest
    ): Student {
        return api
            .createStudent(request)
            .toDomain()
    }
}
