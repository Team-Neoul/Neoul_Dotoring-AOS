package com.example.dotoring_neoul.dto

import com.google.gson.JsonElement

// 공통 응답
data class CommonResponse(
    val success: Boolean,
    val response: JsonElement?,
    val error: CommonErrorResponse
)