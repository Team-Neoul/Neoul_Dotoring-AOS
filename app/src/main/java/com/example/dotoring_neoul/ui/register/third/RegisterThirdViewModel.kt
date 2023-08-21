package com.example.dotoring_neoul.ui.register.third

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.register.NicknameValidationRequest
import com.example.dotoring_neoul.network.DotoringRegisterAPI
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterThirdViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterThirdUiState())
    val uiState: StateFlow<RegisterThirdUiState> = _uiState.asStateFlow()

    var nickname by mutableStateOf("")
        private set

    var btnState by mutableStateOf(false)

    fun updateNickname(nicknameInput: String) {
        nickname = nicknameInput

        _uiState.update { currentState ->
            currentState.copy(nickname = nicknameInput)
        }
    }

    fun updateNicknameCertifiedState() {
        _uiState.update { currentState ->
            currentState.copy(nicknameCertified = true)
        }
    }
    fun toggleNicknameErrorTextColor() {
        if( _uiState.value.nicknameCertified ) {
            _uiState.update { currentState ->
                currentState.copy(nicknameErrorColor = Color.Transparent)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(nicknameErrorColor = Color(0xffff7B7B))
            }
        }
    }
    fun enableBtnState() {
        btnState = true

        _uiState.update { currentState ->
            currentState.copy(btnState = btnState)
        }
    }

    fun verifyNickname() {
        val verifyNicknameRequest = NicknameValidationRequest(nickname = uiState.value.nickname)
        Log.d("통신", "Request: ${verifyNicknameRequest.toString()}")
        val verifyNicknameResponseCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.nicknameValidation(verifyNicknameRequest)

        verifyNicknameResponseCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {

                Log.d("통신", "onResponse")
                Log.d("통신", "닉네임 중복 확인: ${response.body()}")
                Log.d("통신", "닉네임 중복 확인: ${response.code()}")


                if (response.isSuccessful && response.body() != null) {
                    Log.d("통신", "Success")

                    _uiState.update { currentState ->
                        currentState.copy(nicknameCertified = true)
                    }

                    updateNicknameCertifiedState()
                    toggleNicknameErrorTextColor()
                    enableBtnState()
                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                val string = t.message.toString()
                Log.d("통신", "통신 실패: $string")
                Log.d("통신", "요청 내용 - $verifyNicknameResponseCall")
            }
        })


    }

}