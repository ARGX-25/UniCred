package com.example.unicred.models.network.api


import com.example.unicred.models.network.CreateStudentRequest
import com.example.unicred.models.network.StudentDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface StudentApi {

    @GET("/university/students")
    suspend fun getStudents(): List<StudentDTO>

    @POST("/university/students")
    suspend fun createStudent(
        @Body payload: CreateStudentRequest
    ): StudentDTO
}
