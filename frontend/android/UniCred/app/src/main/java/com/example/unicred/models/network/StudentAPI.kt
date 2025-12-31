package com.example.unicred.models.network


import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface StudentApi {

    @GET("/students")
    suspend fun getStudents(): List<StudentDTO>

    @POST("/students")
    suspend fun createStudent(
        @Body payload: CreateStudentRequest
    ): StudentDTO
}
