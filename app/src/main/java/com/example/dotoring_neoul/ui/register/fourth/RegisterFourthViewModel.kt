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

    var btnState by mutableStateOf(false)
        private set

    fun updateIntroductionInput(introduction: String) {
        introductionInput = introduction

        _uiState.update { currentState ->
            currentState.copy(introduction = introductionInput)
        }
    }

    fun enableNextButton() {
        btnState = true

        _uiState.update { currentState ->
            currentState.copy(btnState = btnState)
        }
    }
}