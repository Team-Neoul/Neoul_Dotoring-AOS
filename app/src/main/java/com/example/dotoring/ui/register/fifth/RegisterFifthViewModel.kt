package com.example.dotoring.ui.register.fifth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterFifthViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterFifthUiState())
    val uiState: StateFlow<RegisterFifthUiState> = _uiState.asStateFlow()

    var acceptance by mutableStateOf(false)
        private set
    var btnState by mutableStateOf(false)
        private set

    /**
     * 동의합니다 버튼 toggle하는 함수
     */
    fun accept () {
        acceptance = !acceptance
        btnState = acceptance
        _uiState.update { currentState ->
            currentState.copy(
                acceptance = acceptance,
                btnState = btnState)
        }
    }
}