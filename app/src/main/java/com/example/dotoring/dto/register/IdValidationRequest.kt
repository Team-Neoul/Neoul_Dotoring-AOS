package com.example.dotoring.dto.register

// 아이디 중복확인 요청 DTO
data class IdValidationRequest(
    val loginId: String
)