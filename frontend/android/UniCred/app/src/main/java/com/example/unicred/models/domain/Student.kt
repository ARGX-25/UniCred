package com.example.unicred.models.domain

data class Student(
    val id: Int,
    val studentId: String,
    val name: String,
    val email: String,
    val program: String,
    val phone: String?,
    val address: String?,
    val status: String
)
