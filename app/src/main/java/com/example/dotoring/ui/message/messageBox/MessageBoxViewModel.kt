package com.example.dotoring.ui.message.messageBox

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.navigation.MessageDetailScreen
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

/**
 * renderMessageBoxScreen: 채팅방 내역을 렌더링
 * MessageBoxScreen에 들어가면 renderMessageBoxScreen이 실행
 */

class MessageBoxViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MessageBoxUiState())
    val uiState: StateFlow<MessageBoxUiState> = _uiState.asStateFlow()


    fun goToMessageDetailScreen(navController: NavHostController) {
        navController.navigate(MessageDetailScreen.MessageDetailed.route)
    }

    fun renderMessageBoxScreen(navController: NavHostController) {
        Log.d("쪽지함", "통신 성공 :")

        val renderMessageBoxRequestCall: Call<CommonResponse> =
            DotoringAPI.retrofitService.loadMessageBox()
        Log.d("쪽지함", "여긴가 :" + renderMessageBoxRequestCall)
        renderMessageBoxRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {


                Log.d("쪽지함", "통신 성공 :" +response.body())
                Log.d("쪽지함", "통신?"+response.message())
                Log.d("쪽지함", "통신 성공 : ${response.raw()}")
                Log.d("쪽지함", "통신 성공 : " + response.isSuccessful)
                val jsonObject= Gson().toJson(response.body())
                Log.d("홈", "통로그인??"+response.body() )
                Log.d("쪽지함", "통들어옴" )
                val jo = JSONObject(jsonObject)
                Log.d("쪽지함","통통들어옴")
                val jsonObjectSuccess = jo.getBoolean("success")


                if (jsonObjectSuccess) {
                    val jsonObjectArray = jo.getJSONArray("response")
                    val uiMessageBoxList: MutableList<MessageBox> = mutableListOf()


                    for (i in 0 until 7) {
                        Log.d("로그인" + " i", i.toString())
                        val getObject = jsonObjectArray.getJSONObject(i)



                        val messagebox = MessageBox(
                            roomPK = getObject.getLong("roomPK"),
                            memberPK = getObject.getLong("memberPK"),
                            nickname = getObject.getString("nickname"),
                            lastLetter = getObject.getString("lastLetter"),
                            major = getObject.getString("major"),
                            updateAt = getObject.getString("updateAt")
                        )

                        uiMessageBoxList.add(messagebox)
                    }

                    _uiState.update { currentState ->
                        currentState.copy(messageList = uiMessageBoxList)
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("메세지박스 통신", "요청 내용 - $renderMessageBoxRequestCall")

            }
        })
    }
}