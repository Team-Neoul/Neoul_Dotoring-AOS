package com.example.dotoring_neoul.ui.login

import com.example.dotoring_neoul.MyApplication
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.login.LoginRequest
import com.example.dotoring_neoul.network.DotoringAPI
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dotoring_neoul.navigation.Graph
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Base64

/**
 * id, pwd 상태관리
 * login 관련 통신
 */
class LoginViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()


    fun updateId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(id = idInput)
        }
    }
    fun updatePwd(pwdInput: String) {
        _uiState.update { currentState ->
            currentState.copy(pwd = pwdInput)
        }
    }

    fun updateBtnState () {
        if ( uiState.value.id =="" || uiState.value.pwd=="")
        {
            _uiState.update { currentState ->
                currentState.copy(btnState = false)
            }
        }
        else{
            _uiState.update { currentState ->
                currentState.copy(btnState = true)
            }
        }
    }


    /**
     * 입력된 id와 pwd 백엔드에 post 요청
     * 통신 성공 시 success, response, code 값 받음
     * 로그인 성공 시 header에서 토큰 받아와 시스템 변수(prefs)에 저장
     */
    fun sendLogin(navController: NavHostController) {
        //navController.navigate(Graph.HOME)
        val sendLoginRequest= LoginRequest(loginId = uiState.value.id, password = uiState.value.pwd)
        Log.d("리퀘","리퀘실헹" + "loginId")
        val sendLoginRequestCall: Call<CommonResponse> = DotoringAPI.retrofitService.doLogin(sendLoginRequest)
        Log.d("통신", "ㅌ통신함수 실행:")

        sendLoginRequestCall.enqueue(object : Callback<CommonResponse>
        {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                val jsonObject= Gson().toJson(response.body())
                Log.d("로그인", "로그인??" )
                val jo = JSONObject(jsonObject)
                Log.d("f로그인","로그인 성공할락말락" +jo)
                val jsonObjectSuccess = jo.getBoolean("success")
                Log.d("로그인", "ㅌ통신성공??:")

                if (jsonObjectSuccess) {
                    Log.d("로그인", "ㅌ통신함수 성공:")
                    val accessToken= response.headers()["Authorization"]
                    val refreshToken= response.headers()["set-cookie"]

                    Log.d("로그인", "헤더 추출완료")
                    MyApplication.prefs.setString("Authorization", accessToken)

                    Log.d("로그인", "엑세스"+accessToken)
                    MyApplication.prefs.setRefresh("Cookie", refreshToken)
                    //MyApplication.token_prefs.refreshToken = refreshToken
                    Log.d("로그인", "리프레쉬"+refreshToken)

                    if(accessToken != null) {
                        val isMentor = decodeToken(accessToken)
                        MyApplication.prefs.setBoolean("isMentor", isMentor)
                    }

                    navController.navigate(Graph.HOME)
                    Log.d("로그인", "컨트롤러")
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("로그인", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $sendLoginRequestCall")
            }
        })
    }

    private fun decodeToken(jwt: String): Boolean {
        val parts = jwt.split(".")
        Log.d("복호화", "decodeToken 실행중")

        val charset = charset("UTF-8")
        val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)),charset)

        val isMentor = JSONObject(payload).getString("aud")

        Log.d("복호화", "isMentor: $isMentor")

        return isMentor == "MENTO"
    }
}