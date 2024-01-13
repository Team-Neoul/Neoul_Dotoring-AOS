package com.example.dotoring.ui.mypage.updateinfo

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SubmitDocUiState(
    val enrollmentFile: Uri? = null,
    val isEnrollmentFileSelected: Boolean = false,
)

class SubmitDocViewModel: ViewModel() {
    private val _submitDocUiState = MutableStateFlow(SubmitDocUiState())
    val submitDocUiState = _submitDocUiState.asStateFlow()

    fun updateEnrollmentFile(enrollmentFile: Uri) {
        _submitDocUiState.update { currentState ->
            currentState.copy(
                enrollmentFile = enrollmentFile
            )
        }
    }

    fun updateFileState(isFileSelected: Boolean) {
        _submitDocUiState.update { currentState ->
            currentState.copy(isEnrollmentFileSelected = isFileSelected)
        }
    }
}