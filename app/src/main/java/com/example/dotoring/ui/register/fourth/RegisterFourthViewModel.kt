package com.example.dotoring.ui.register.fourth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class RegisterFourthViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterFourthUiState())
    val uiState: StateFlow<RegisterFourthUiState> = _uiState.asStateFlow()


    fun addTag(tag: String) {
        if(uiState.value.tags.size < 3) {
            val originalTagList = uiState.value.tags
            val updatedTagList = originalTagList + tag

            _uiState.update { currentState ->
                currentState.copy(tags = updatedTagList)
            }
        } else {
            /* TODO */
        }
    }

    fun deleteTag(tag: String) {
        if(uiState.value.tags.isNotEmpty()) {
            val originalTagList = uiState.value.tags
            val updatedTagList = originalTagList - tag

            _uiState.update { currentState ->
                currentState.copy(tags = updatedTagList)
            }
        } else {
            /* TODO */
        }
    }
}