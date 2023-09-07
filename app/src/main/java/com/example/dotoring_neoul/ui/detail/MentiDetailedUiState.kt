package com.example.dotoring_neoul.ui.detail

/**
 * 멘티 세부 정보 화면 UiState
 */
data class MentiDetailedUiState(
    val profileImage: String = "",
    val nickname: String = "도토리",
    val job: String = "정보통신",
    val major: String = "소프트웨어공학과",
    val introduction: String = "안녕하세요. 전남대학교에 다니는 소프트웨어공학과 21학번 도토리입니다.",
    val mentoring: String = "비대면 멘토링 희망합니다."
)
