package com.example.dotoring_neoul.ui.login

/**
 * id: 사용자 입력 id
 * pwd: 사용자 입력 password
 * btnState: id와 pwd가 입력되면 활성화되도록 State 설정
 */
data class LoginUiState (
    val id: String = "",
    val pwd: String = "",

    val btnState: Boolean = false
)