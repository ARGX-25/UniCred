package com.example.unicred.repositories

import com.example.unicred.models.domain.Student
import com.example.unicred.models.network.CreateStudentRequest

interface StudentRepository {

    suspend fun getStudents(): List<Student>

    suspend fun createStudent(
        request: CreateStudentRequest
    ): Student
}
