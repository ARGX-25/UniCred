package com.example.unicred.models.network

import com.google.gson.annotations.SerializedName

data class CredentialDTO(
    val id: Int,
    val title: String,

    @SerializedName("credential_type")
    val credentialType: String,

    @SerializedName("graduation_date")
    val graduationDate: String,

    val hash: String,
    val status: String,

    @SerializedName("issued_at")
    val issuedAt: String,

    @SerializedName("revoked_at")
    val revokedAt: String?,

    @SerializedName("student_id")
    val studentId: String,

    @SerializedName("student_name")
    val studentName: String
)
