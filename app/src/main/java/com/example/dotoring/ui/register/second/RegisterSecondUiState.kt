package com.example.dotoring.ui.register.second

import android.net.Uri

data class RegisterSecondUiState(
    // 파일 Uri
    val employmentCertification: Uri? = null,
    val graduationCertification: Uri? = null,

    // 파일 업로드 여부
    val employmentFileUploaded: Boolean = false, // nextBtnState와 동일한 역할.
    val graduationFileUploaded: Boolean = false,
)
