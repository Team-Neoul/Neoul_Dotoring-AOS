package com.example.dotoring_neoul.ui.home

/**
 * 멘티 카드에 들어가는 멘티 정보 표시
 */
data class Member(
    val nickname: String,
    val profileImage: String,
    val major: String,
    val mentoringField: String,
    val introduction: String
)