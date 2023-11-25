package com.example.dotoring.ui.login.findId

data class FindIdUiState (
    val email: String = "",
    val code: String = "",

    val emailState: Boolean = false,
    val codeState: Boolean = false
)