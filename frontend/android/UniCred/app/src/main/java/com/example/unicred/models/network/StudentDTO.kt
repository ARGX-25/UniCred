package com.example.unicred.models.network

import com.google.gson.annotations.SerializedName

data class StudentDTO(
    val id: Int,

    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("university_id")
    val universityId: Int,

    @SerializedName("student_id")
    val studentId: String,

    val program: String,

    @SerializedName("phone_number")
    val phoneNumber: String?,

    val address: String?,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("full_name")
    val fullName: String,

    val email: String,

    @SerializedName("valid_id")
    val validId: Boolean,

    @SerializedName("user_status")
    val userStatus: String
)
