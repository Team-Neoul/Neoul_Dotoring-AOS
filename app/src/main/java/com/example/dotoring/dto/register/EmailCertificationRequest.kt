package com.example.dotoring.dto.register

// 이메일 인증 요청 DTO
data class EmailCertificationRequest(
    val emailVerificationCode: String,
    val email: String
)