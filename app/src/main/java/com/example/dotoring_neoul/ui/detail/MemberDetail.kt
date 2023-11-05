package com.example.dotoring_neoul.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 멤버 세부 정보
 */
@Parcelize
class MemberDetail (
    val profileImage: String,
    val major: String,
    val grade: String,
    val nickname: String,
    val mentoringField: String,
    val introduction: String
): Parcelable