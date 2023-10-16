package com.example.dotoring_neoul.dto.register

// 인증서를 제외한 멘토 정보가 담긴 DTO
data class SaveMentoRqDTO(
    val company: String,
    val careerLevel: Int,
    val field: String,
    val major: String,
    val nickname: String,
    val introduction: String,
    val loginId: String,
    val password: String,
    val email: String
)