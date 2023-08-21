package com.example.dotoring_neoul.ui.register.second

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class RegisterSecondViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegisterSecondUiState())
    val uiState: StateFlow<RegisterSecondUiState> = _uiState.asStateFlow()

//    fun uriToFile(imageUri: Uri?): File {
//        val path = imageUri?.path
//
//        return File(path.toString())
//
//        /*if (file.absolutePath == path) {
//            Log.d("테스트", "파일 패스 테스트 성공")
//        }
//
//        return file*/
//
//    }

    fun updateEmploymentCertification(imageUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                employmentCertification = imageUri)
        }
    }

    fun updateGraduationCertification(imageUri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(
                graduationCertification = imageUri)
        }
    }
    fun uploadEmploymentFile() {
        _uiState.update { currentState ->
            currentState.copy(
                employmentFileUploaded = true)
        }
    }

    fun uploadGraduationFile() {
        _uiState.update { currentState ->
            currentState.copy(graduationFileUploaded = true)
        }
    }

}