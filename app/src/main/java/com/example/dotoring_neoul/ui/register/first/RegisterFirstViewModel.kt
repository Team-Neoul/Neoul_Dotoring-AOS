package com.example.dotoring_neoul.ui.register.first

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterFirstViewModel(): ViewModel() {

    private val _uiState = MutableStateFlow(RegisterFirstUiState())
    val uiState: StateFlow<RegisterFirstUiState> = _uiState.asStateFlow()

    // 회사 텍스트 필드 업데이트
    fun updateUserCompany(userCompany: String) {
        _uiState.update { currentState ->
            currentState.copy(company = userCompany)
        }
    }

    // 커리어 텍스트 필드 업데이트
    fun updateUserCareer(userCareer: String) {
        _uiState.update { currentState ->
            currentState.copy(careerLevel = userCareer)
        }
    }

    // 직무 텍스트 필드 업데이트
    fun updateUserJob(userJob: String) {
        _uiState.update { currentState ->
            currentState.copy(job = userJob)
        }
    }

    // 학과 텍스트 필드 업데이트
    fun updateUserMajor(userMajor: String) {
        _uiState.update { currentState ->
            currentState.copy(major = userMajor)
        }
    }

    // 회사 텍스트 필드 null 여부
    fun updateCompanyFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = true)
            }
        }
    }

    // 커리어 텍스트 필드 null 여부
    fun updateCareerFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = true)
            }
        }
    }

    // 직무 텍스트 필드 null 여부
    fun updateJobFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = true)
            }
        }
    }

    // 학과 텍스트 필드 null 여부
    fun updateMajorFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = true)
            }
        }
    }

    // 다음 버튼 활성화
    fun enableNextButton() {
        _uiState.update { currentState ->
            currentState.copy(firstBtnState = true)
        }
    }

    // 직무 리스트 불러오는 통신
    fun loadJobList() {
    }

    // 학과 리스트 불러오는 통신
    fun loadMajorList() {
    }

}