package com.example.dotoring.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.network.DotoringAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MemberDetailedViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemberDetailedUiState())
    val uiState: StateFlow<MemberDetailedUiState> = _uiState.asStateFlow()

    /**
     * 멤버 정보 불러오기 위한 함수
     */
    fun loadMemberInfo(
        isMentor: Boolean,
        memberId: Int
    ) {
        if (isMentor) {
            loadMenteeDetailedInfo(memberId)
        } else {
            loadMentorDetailedInfo(memberId)
        }
    }

    private fun loadMenteeDetailedInfo(id: Int) {
        DotoringAPI.retrofitService.loadMenteeDetailedInfo(id = id)
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    if (response.isSuccessful) {
                        val json = Gson().toJson(response.body())
                        val jsonObject = JSONObject(json.toString())
                        val responseJsonObject = jsonObject.getJSONObject("response")
                        val fields = responseJsonObject.optJSONArray("fields")

                        if (fields != null && fields.length() > 0) {
                            val uiFieldList: MutableList<String> = mutableListOf()

                            for (i in 0 until fields.length()) {

                                val fieldString = fields.optString(i)
                                uiFieldList.add(fieldString)

                            }

                            _uiState.update { currentState ->
                                currentState.copy(fields = uiFieldList)
                            }
                        }

                        val majors = responseJsonObject.optJSONArray("majors")
                        if (majors != null && majors.length() > 0) {
                            val uiMajorList: MutableList<String> = mutableListOf()

                            for (i in 0 until majors.length()) {

                                val majorString = majors.optString(i)
                                uiMajorList.add(majorString)

                            }

                            _uiState.update { currentState ->
                                currentState.copy(majors = uiMajorList)
                            }
                        }

                        val profileImage = responseJsonObject.getString("profileImage")
                        Log.d("멘티 디테일 로드", "loadMenteeDetailed - profileImage: $profileImage")
                        val replacedProfileImage = profileImage.toString()
                            .replace("\\", "")
                            .replace("localhost", "10.0.2.2")
                        val nickname = responseJsonObject.getString("nickname")
                        val introduction = responseJsonObject.getString("introduction")
                        val grade = responseJsonObject.getString("grade")

                        _uiState.update { currentState ->
                            currentState.copy(
                                profileImage = replacedProfileImage,
                                nickname = nickname,
                                introduction = introduction,
                                grade = grade
                            )
                        }
                    } else {
                        val errorResponse = DotoringAPI.getErrorResponse(response.errorBody()!!)
                        val json = Gson().toJson(errorResponse)
                        val jsonObject = JSONObject(json)
                        val jsonObjectError = jsonObject.getJSONObject("error")
                        val errorMessage = jsonObjectError.getString("message")
                        Log.d("멘티 디테일 로드", errorMessage)
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "인터넷 연결이 끊겼습니다."
                        is HttpException -> "알 수 없는 오류가 발생했어요."
                        else -> t.localizedMessage
                    }
                }
            })
    }

    private fun loadMentorDetailedInfo(id: Int) {
        DotoringAPI.retrofitService.loadMentorDetailedInfo(id = id)
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {

                    if (response.isSuccessful) {
                        val json = Gson().toJson(response.body())
                        val jsonObject = JSONObject(json.toString())
                        val responseJsonObject = jsonObject.getJSONObject("response")

                        val fields = responseJsonObject.optJSONArray("fields")
                        if (fields != null && fields.length() > 0) {
                            val uiFieldList: MutableList<String> = mutableListOf()

                            for (i in 0 until fields.length()) {
                                val fieldString = fields.optString(i)
                                uiFieldList.add(fieldString)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(fields = uiFieldList)
                            }
                        }


                        val majors = responseJsonObject.optJSONArray("majors")
                        if (majors != null && majors.length() > 0) {
                            val uiMajorList: MutableList<String> = mutableListOf()

                            for (i in 0 until majors.length()) {
                                val majorString = majors.optString(i)
                                uiMajorList.add(majorString)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(majors = uiMajorList)
                            }
                        }

                        val profileImage = responseJsonObject.getString("profileImage")
                        Log.d("멘토 디테일 로드", "loadMenteeDetailed - profileImage: $profileImage")
                        val replacedProfileImage = profileImage.toString()
                            .replace("\\", "")
                            .replace("localhost", "10.0.2.2")
                        val nickname = responseJsonObject.getString("nickname")
                        val introduction = responseJsonObject.getString("introduction")
                        val grade = responseJsonObject.getString("grade")

                        _uiState.update { currentState ->
                            currentState.copy(
                                profileImage = replacedProfileImage,
                                nickname = nickname,
                                introduction = introduction,
                                grade = grade
                            )
                        }
                    } else {
                        val errorResponse = DotoringAPI.getErrorResponse(response.errorBody()!!)
                        val json = Gson().toJson(errorResponse)
                        val jsonObject = JSONObject(json)
                        val jsonObjectError = jsonObject.getJSONObject("error")
                        val errorMessage = jsonObjectError.getString("message")
                        Log.d("멘토 디테일 로드", errorMessage)
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "인터넷 연결이 끊겼습니다."
                        is HttpException -> "알 수 없는 오류가 발생했어요."
                        else -> t.localizedMessage
                    }
                }
            })
    }
}
