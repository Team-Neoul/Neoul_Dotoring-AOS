package com.example.dotoring.ui.login.findId

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.dto.login.FindIdRequest
import com.example.dotoring.dto.register.EmailCodeRequest
import com.example.dotoring.network.DotoringAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FindIdUiState())
    val uiState: StateFlow<FindIdUiState> = _uiState.asStateFlow()


    fun updateEmail(emailInput: String) {
        _uiState.update { currentState ->
            currentState.copy(email = emailInput)
        }
    }

    fun updateEmailState(boolean: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(emailState = boolean)
        }
    }

    fun updateCodeState(boolean: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(codeState = boolean)
        }
    }

    fun getVerifyCode(navController: NavHostController) {
        //navController.navigate(Graph.HOME)
        val sendEmailRequest= EmailCodeRequest(email = uiState.value.email)
        Log.d("리퀘","리퀘실헹" + "loginId")
        val sendEmailRequestCall: Call<CommonResponse> = DotoringAPI.retrofitService.getCode(sendEmailRequest)
        Log.d("통신", "ㅌ통신함수 실행:")

        sendEmailRequestCall.enqueue(object : Callback<CommonResponse>
        {


            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                Log.d("로그인", "통신 성공 :" +response.body())
                Log.d("로그인", "통신?"+response.message())
                Log.d("로그인", "통신 성공 : ${response.raw()}")
                Log.d("로그인", "통신 성공 : " + response.isSuccessful)
                val jsonObject= Gson().toJson(response.body())
                Log.d("로그인", "로그인??" )
                val jo = JSONObject(jsonObject)
                Log.d("f로그인","로그인 성공할락말락" +jo)
                val jsonObjectSuccess = jo.getBoolean("success")
                Log.d("로그인", "ㅌ통신성공??:")

                if (jsonObjectSuccess) {
                    updateEmailState(true)

                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("로그인", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $sendEmailRequestCall")

            }
        })

    }

    fun sendVerifyCode(navController: NavHostController) {
        //navController.navigate(Graph.HOME)
        val sendCodeRequest= FindIdRequest(email = uiState.value.email, code= uiState.value.code)
        Log.d("리퀘","리퀘실헹" + "loginId")
        val sendCodeRequestCall: Call<CommonResponse> = DotoringAPI.retrofitService.findId(sendCodeRequest)
        Log.d("통신", "ㅌ통신함수 실행:")

        sendCodeRequestCall.enqueue(object : Callback<CommonResponse>
        {


            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                Log.d("로그인", "통신 성공 :" +response.body())
                Log.d("로그인", "통신?"+response.message())
                Log.d("로그인", "통신 성공 : ${response.raw()}")
                Log.d("로그인", "통신 성공 : " + response.isSuccessful)
                val jsonObject= Gson().toJson(response.body())
                Log.d("로그인", "로그인??" )
                val jo = JSONObject(jsonObject)
                Log.d("f로그인","로그인 성공할락말락" +jo)
                val jsonObjectSuccess = jo.getBoolean("success")
                Log.d("로그인", "ㅌ통신성공??:")

                if (jsonObjectSuccess) {
                    updateCodeState(true)



                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("로그인", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $sendCodeRequestCall")

            }
        })

    }
}