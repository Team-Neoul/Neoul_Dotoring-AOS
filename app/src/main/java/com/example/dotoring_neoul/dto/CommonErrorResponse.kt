package com.example.dotoring_neoul.dto

// 공통 에러 응답
data class CommonErrorResponse(
    val message: String,
    val code: Int,
    val status: String
)
