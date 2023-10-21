package com.example.dotoring_neoul.ui.util.register

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MenteeInformation(
    val school: String = "",
    val grade: Int = 1,
    val field: List<String> = mutableListOf(),
    val major: List<String> = mutableListOf(),
    val enrollmentCertification: Uri? = null,
    val nickname: String = "",
    val introduction: String = "",
    val loginId: String = "",
    val password: String = "",
    val email: String = ""
): Parcelable