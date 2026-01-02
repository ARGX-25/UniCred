package com.example.unicred.models.domain

data class Student(
    val id: Int,
    val fullName: String,
    val email: String,
    val studentId: String,
    val program: String,
    val phoneNumber: String?,
    val address: String?,
    val validId: String,
    val status: String,
    val createdAt: String
)

