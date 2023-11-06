package com.example.dotoring_neoul.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dotoring_neoul.dto.CommonResponse
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

class MemberDetailedViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MemberDetailedUiState())
    val uiState: StateFlow<MemberDetailedUiState> = _uiState.asStateFlow()

    /**
     * 멤버 정보 불러오기 위한 함수
     */
    fun loadMemberInfo(
        isMentor: Boolean,
        memberId: Int
    ) {
        if(isMentor) {
            loadMenteeDetailedInfo(memberId)
        } else {
            loadMentorDetailedInfo(memberId)
        }
    }

    private fun loadMenteeDetailedInfo(id: Int) {
        Log.d("멘티 디테일 로드", "loadMenteeDetailed 실행")

        DotoringAPI.retrofitService.loadMenteeDetailedInfo(id = id)
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - onResponse")
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - response.code(): ${response.code()}")
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - response.body(): ${response.body()}")

                    val json = Gson().toJson(response.body())
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - json: $json")

                    val jsonObject = JSONObject(json.toString())
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - jsonObject: $jsonObject")

                    val jsonObjectSuccess = jsonObject.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("멘티 디테일 로드", "loadMenteeDetailed - success")

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        Log.d("멘티 디테일 로드", "loadMenteeDetailed - responseJsonObject: $responseJsonObject")


                        val fields = responseJsonObject.optJSONArray("fields")
                        Log.d("멘티 디테일 로드", "loadMenteeDetailed - field: $fields")
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
                        Log.d("멘티 디테일 로드", "loadMenteeDetailed - 응답이 실패하거나 데이터가 없습니다.")
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("멘티 디테일 로드", "loadMenteeDetailed - 통신 실패: $t")
                }
            })
    }

    private fun loadMentorDetailedInfo(id: Int) {
        Log.d("멘토 디테일 로드", "loadMenteeDetailed 실행")

        DotoringAPI.retrofitService.loadMentorDetailedInfo(id = id)
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - onResponse")
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - response.code(): ${response.code()}")
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - response.body(): ${response.body()}")

                    val json = Gson().toJson(response.body())
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - json: $json")

                    val jsonObject = JSONObject(json.toString())
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - jsonObject: $jsonObject")

                    val jsonObjectSuccess = jsonObject.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("멘토 디테일 로드", "loadMenteeDetailed - success")

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        Log.d("멘토 디테일 로드", "loadMenteeDetailed - responseJsonObject: $responseJsonObject")

                        val fields = responseJsonObject.optJSONArray("fields")
                        Log.d("멘토 디테일 로드", "loadMenteeDetailed - field: $fields")
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
                        Log.d("멘토 디테일 로드", "loadMenteeDetailed - 응답이 실패하거나 데이터가 없습니다.")
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("멘토 디테일 로드", "loadMenteeDetailed - 통신 실패: $t")
                }
            })
    }
}
