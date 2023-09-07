package com.example.dotoring_neoul.ui.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 멘티 세부 정보
 */
@Parcelize
class MenteeDetail (
    val major: String,
    val nickname: String,
    val profileImage: String,
    val job: String,
    val introduction: String,
    val mentoring: String
): Parcelable