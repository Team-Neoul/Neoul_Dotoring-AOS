package com.example.dotoring.dto.login

data class FindIdRequest(
    val email: String,
    val code: String
)