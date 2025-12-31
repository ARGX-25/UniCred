package com.example.unicred.models.domain

import com.example.unicred.models.network.StudentDTO

fun StudentDTO.toDomain(): Student {
    return Student(
        id = id,
        studentId = studentId,
        name = fullName,
        email = email,
        program = program,
        phone = phoneNumber,
        address = address,
        status = userStatus
    )
}

fun List<StudentDTO>.toDomainList(): List<Student> {
    return map { it.toDomain() }
}
