package com.example.dotoring.dto

import com.google.gson.JsonElement

// 공통 응답
data class CommonResponse(
    val success: Boolean,
    val response: JsonElement?,
    val error: CommonErrorResponse
)