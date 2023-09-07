package com.example.dotoring_neoul.ui.message.messageDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.message.MessageRequest
import com.example.dotoring_neoul.network.DotoringAPI
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
 * 쪽지함 상세의 기능 및 통신 구현
 * writeContent 상태관리, sendMessage, renderMessageDetailScreen 기능 구현
 */
class MessageDetailViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(MessageDetailUiState())
    val uiState: StateFlow<MessageDetailUiState> = _uiState.asStateFlow()


//    val uiMessageDetailList: MutableList<MessageDetail> = mutableListOf()

//    fun updateContent(messageDetail: MessageDetail) {
//        _uiState.update { currentState ->
//            currentState.copy(chatList = uiMessageDetailList)
//        }
//    }

    fun updateContent(writeinput: String) {
        _uiState.update { currentState ->
            currentState.copy(writeContent = writeinput)
        }
    }

    /**
     * 쪽지 송신 기능 구현
     * sendMessage: writeContent에 작성한 내용 송신하는 기능 구현,송신 후 renderMessageDetailScreen을 호출하여 리렌더링
     */
    fun sendMessage(navController: NavHostController){
        val sendMessageRequest= MessageRequest(content = uiState.value.writeContent)
        Log.d("송신", "통신함수 실행:" + sendMessageRequest.content)
        val sendMessageRequestCall: Call<CommonResponse> =
            DotoringAPI.retrofitService.inSendMessage(sendMessageRequest)
        Log.d("송신", "통신함수 실행:" )

        sendMessageRequestCall.enqueue(object : Callback<CommonResponse>
        {


            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                Log.d("송신", "통신 성공 : ${response.raw()}")
                Log.d("송신", "통신 성공 : " + response.body().toString())

                val jsonObject= Gson().toJson(response.body())
                Log.d("송신","로그인 성공할락말락")
                val jo = JSONObject(jsonObject)
                val jsonObjectSuccess = jo.getBoolean("success")
                Log.d("송신", "ㅌ통신성공??:")

                if (jsonObjectSuccess) {
                    Log.d("송신", "ㅌ통신함수 성공:")
                    renderMessageDetailScreen(navController, 1)
//                    renderMessageDetailScreen(navController)

                }
            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $sendMessageRequestCall")

            }
        })

    }

    //    fun loadMentiInfo(menteeDetail: MenteeDetail) {
//        Log.d("업데이트", "loadMentiInfo 실행")
//        _uiState.update { currentState ->
//            currentState.copy(profileImage = menteeDetail.profileImage,
//                nickname = menteeDetail.nickname,
//                job = menteeDetail.job,
//                major = menteeDetail.major,
//                introduction = menteeDetail.introduction,
//                mentoring = menteeDetail.mentoring)
//        }
//
//    }


    /**
     * 쪽지 상세 렌더링 기능 구현
     * renderMessageDetailScreen: 메시지 리스트를 서버에서 받아와 하나씩 화면에 띄움
     * */
    fun renderMessageDetailScreen(navController: NavHostController, roomPk: Long) {


//        val renderMessageDetailRequestCall: Call<CommonResponse> =
        DotoringAPI.retrofitService.loadDetailedMessage(
            roomPk = 1, page=0, size=6).enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {

                Log.d("메세지", "통신 성공 : ${response.raw()}")
                Log.d("메세지", "통신 성공 : " + response.body().toString())
                val jsonObject = Gson().toJson(response.body())
                Log.d("메세지", "로그인 성공할락말락")
                val jo = JSONObject(jsonObject.toString())
                val jsonObjectSuccess = jo.getBoolean("success")
                Log.d("메세지", "ㅌ통신성공??:")

                if (jsonObjectSuccess) {

                    val responseJsonObject = jo.getJSONObject("response")
                    val chatList = responseJsonObject.optJSONArray("content")

                    val jsonObjectArray = jo.getJSONObject("response")
                    val messageDetail = jsonObjectArray.optJSONArray("content")

//                val getContentObject = jsonObjectArray.getJSONObject(1)
//                val jsonContentArray = getContentObject.getJSONArray("content")


//
//
                    if (chatList != null && chatList.length() > 0) {
                        val uiMessageDetailList: MutableList<MessageDetail> = mutableListOf()

                        for (i in 0 until chatList.length()) {
                            val messageObject = chatList.optJSONObject(i)
//                    val time=getObject.getString("createdAt")


                            val messageDetail = MessageDetail(
                                nickname = messageObject.getString("nickname"),
                                letterId = messageObject.getLong("letterId"),
                                content = messageObject.getString("content"),
                                writer = messageObject.getBoolean("writer"),
                                createdAt = messageObject.getString("createdAt")
                            )

                            uiMessageDetailList.add(messageDetail)
                        }

                        _uiState.update { currentState ->
                            currentState.copy(chatList = uiMessageDetailList)
                        }
                    }
                }
            }



            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("메세지박스 통신", "요청 내용 - $t")

            }
        })
    }
}
