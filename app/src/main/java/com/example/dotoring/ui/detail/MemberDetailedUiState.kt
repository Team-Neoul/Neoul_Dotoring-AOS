package com.example.dotoring.ui.detail

/**
 * 멘티 세부 정보 화면 UiState
 */
data class MemberDetailedUiState(
    val profileImage: String? = "",
    val nickname: String? = "도토리",
    val fields: List<String>? = listOf(),
    val majors: List<String>? = listOf(),
    val introduction: String? = "안녕하세요. 전남대학교에 다니는 소프트웨어공학과 21학번 도토리입니다.",
    val grade: String? = "3학년"
)
