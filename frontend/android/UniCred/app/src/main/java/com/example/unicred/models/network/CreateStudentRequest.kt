package com.example.unicred.models.network

import com.google.gson.annotations.SerializedName

data class CreateStudentRequest(
    @SerializedName("full_name")
    val fullName: String,

    val email: String,

    val password: String,

    @SerializedName("student_id")
    val studentId: String,

    val program: String,

    @SerializedName("phone_number")
    val phoneNumber: String? = null,

    val address: String? = null
)
