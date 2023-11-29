package com.example.dotoring.ui.util.register

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MentorInformation(
    val company: String = "",
    val careerLevel: Int = 1,
    val field: List<String> = mutableListOf(),
    val major: List<String> = mutableListOf(),
    val employmentCertification: Uri? = null,
    val graduateCertification: Uri? = null,
    val nickname: String = "",
    val introduction: String = "",
    val loginId: String = "",
    val password: String = "",
    val email: String = ""
): Parcelable