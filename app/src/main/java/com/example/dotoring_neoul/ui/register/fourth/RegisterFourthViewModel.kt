package com.example.dotoring_neoul.ui.register.fourth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class RegisterFourthViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterFourthUiState())
    val uiState: StateFlow<RegisterFourthUiState> = _uiState.asStateFlow()

    var introductionInput by mutableStateOf("")
        private set


    /**
     * 소개 입력받는 텍스트 필드 업데이트
     */
    fun updateIntroductionInput(introduction: String) {
        introductionInput = introduction

        _uiState.update { currentState ->
            currentState.copy(mentorIntroduction = introductionInput)
        }
    }

    /**
     * 회원 가입 네번째 화면 다음 버튼 활성화
     */
    fun updateNextButtonState(nextButtonEnabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(nextButtonState = nextButtonEnabled)
        }
    }
}