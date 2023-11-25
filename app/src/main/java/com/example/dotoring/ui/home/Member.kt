package com.example.dotoring.ui.home

/**
 * 멘티 카드에 들어가는 멘티 정보 표시
 */
data class Member(
    val id: Int,
    val nickname: String,
    val profileImage: String,
    val majors: String,
    val fields: String,
    val introduction: String,
)