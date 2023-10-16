package com.example.dotoring_neoul.ui.register.second

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import okhttp3.RequestBody.Companion.toRequestBody

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
        val filePath = imageUri.path
        val imageFile = File(filePath.toString())


        val test = MultipartBody.Part.createFormData(
            "photo",
            "my-photo.png",
            imageFile.asRequestBody("image/png".toMediaType())
        )

        Log.d("멀티파트바디 테스트", "test data : ${test}")
        _uiState.update { currentState ->
            currentState.copy(
                employmentCertification = imageUri)
        }
    }

    fun updateGraduationCertification(imageUri: Uri) {
        val filePath = imageUri.path
        val imageFile = File(filePath.toString())


        val test = MultipartBody.Part.createFormData(
            "photo",
            "my-photo.png",
            imageFile.asRequestBody("image/png".toMediaType())
        )

        Log.d("멀티파트바디 테스트", "test data : ${test}")
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

    /*fun updateNextBtnState() {
        _uiState.update {currentState ->
            currentState.copy(nextBtnState = true)
        }
    }*/

}