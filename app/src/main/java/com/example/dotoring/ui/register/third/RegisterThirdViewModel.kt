package com.example.dotoring.ui.register.third

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.dto.register.NicknameValidationRequest
import com.example.dotoring.network.DotoringRegisterAPI
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterThirdViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(RegisterThirdUiState())
    val uiState: StateFlow<RegisterThirdUiState> = _uiState.asStateFlow()

    var nickname by mutableStateOf("")
        private set

    fun updateNickname(nicknameInput: String) {
        nickname = nicknameInput

        _uiState.update { currentState ->
            currentState.copy(nickname = nicknameInput)
        }
    }

    fun updateNicknameConditionErrorState(isError: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(nicknameConditionError = isError)
        }

        enableNextButtonState()

        if(!isError) {
            enableDuplicationCheckButtonState(true)
        } else {
            enableDuplicationCheckButtonState(false)
        }
    }

    fun updateNicknameDuplicationErrorState(errorState: DuplicationCheckState) {
        _uiState.update { currentState ->
            currentState.copy(nicknameDuplicationError = errorState)
        }

        enableNextButtonState()
    }

    private fun enableDuplicationCheckButtonState(buttonEnabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(duplicationCheckButtonState = buttonEnabled)
        }
    }

    private fun enableNextButtonState() {
        if (uiState.value.nicknameDuplicationError != DuplicationCheckState.DuplicationCheckSuccess || uiState.value.nicknameConditionError) {
            _uiState.update { currentState ->
                currentState.copy(nextButtonState = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(nextButtonState = true)
            }
        }

    }

    fun verifyMentorNickname() {
        val verifyNicknameRequest = NicknameValidationRequest(nickname = uiState.value.nickname)
        Log.d("닉네임 중복 확인", "Request: ${verifyNicknameRequest.toString()}")
        val verifyNicknameResponseCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.mentoNicknameValidation(verifyNicknameRequest)

        verifyNicknameResponseCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {

                Log.d("닉네임 중복 확인", "onResponse")
                Log.d("닉네임 중복 확인", "닉네임 중복 확인: ${response.body()}")
                Log.d("닉네임 중복 확인", "닉네임 중복 확인: ${response.code()}")

                if( response.code() == 200 ) {
                    updateNicknameDuplicationErrorState(DuplicationCheckState.DuplicationCheckSuccess)

                } else {
                    updateNicknameDuplicationErrorState(DuplicationCheckState.DuplicationCheckFail)
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                val string = t.message.toString()
                Log.d("닉네임 중복 확인", "통신 실패: $string")
                Log.d("닉네임 중복 확인", "요청 내용 - $verifyNicknameResponseCall")
            }
        })
    }

    fun verifyMenteeNickname() {
        val verifyNicknameRequest = NicknameValidationRequest(nickname = uiState.value.nickname)
        Log.d("닉네임 중복 확인", "Request: ${verifyNicknameRequest.toString()}")
        val verifyNicknameResponseCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.menteeNicknameValidation(verifyNicknameRequest)

        verifyNicknameResponseCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {

                Log.d("닉네임 중복 확인", "onResponse")
                Log.d("닉네임 중복 확인", "response.body(): ${response.body()}")
                Log.d("닉네임 중복 확인", "response.code(): ${response.code()}")

                if( response.code() == 200 ) {
                    updateNicknameDuplicationErrorState(DuplicationCheckState.DuplicationCheckSuccess)

                } else {
                    updateNicknameDuplicationErrorState(DuplicationCheckState.DuplicationCheckFail)
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                val string = t.message.toString()
                Log.d("닉네임 중복 확인", "통신 실패: $string")
                Log.d("닉네임 중복 확인", "요청 내용 - $verifyNicknameResponseCall")
            }
        })
    }

}