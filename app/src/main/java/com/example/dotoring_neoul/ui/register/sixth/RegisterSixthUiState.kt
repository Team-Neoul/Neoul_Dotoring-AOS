package com.example.dotoring_neoul.ui.register.sixth

import androidx.compose.ui.graphics.Color

data class RegisterSixthUiState(
    val memberId: String = "",
    val idAvailable: Boolean = false,
    val idError: Boolean = false,
    val idErrorTextColor: Color = Color.Transparent,

    val password: String = "",
    val passwordCertification: String = "",
    val passwordCertified: Boolean = false,
    val passwordErrorTextColor: Color = Color.Transparent,

    val email: String = "",
    val certificationPeriod: String = "",
    val validationCode: String = "",
    val emailValidated: Boolean = false,
    val emailErrorTextColor: Color = Color.Transparent,

    val btnState: Boolean = false
)
