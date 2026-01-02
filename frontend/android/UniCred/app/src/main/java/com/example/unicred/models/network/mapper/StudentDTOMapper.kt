package com.example.unicred.models.domain

import com.example.unicred.models.network.StudentDTO

fun StudentDTO.toDomain(): Student {
    return Student(
        id = id,
        fullName = fullName,
        email = email,
        studentId = studentId,
        program = program,
        phoneNumber = phoneNumber,
        address = address,
        validId = validId,
        status = userStatus,
        createdAt = createdAt
    )
}


fun List<StudentDTO>.toDomainList(): List<Student> {
    return map { it.toDomain() }
}
