package com.example.dotoring_neoul.ui.register.third

import androidx.compose.ui.graphics.Color

data class RegisterThirdUiState(
    val nickname: String = "",
    val nicknameCertified: Boolean = false,
    val nicknameErrorColor: Color = Color.Transparent,

    val btnState: Boolean = false
)
