package com.example.dotoring_neoul.ui.home

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.network.DotoringAPI
import com.example.dotoring_neoul.network.DotoringRegisterAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _selectedMajorList = mutableListOf<String>().toMutableStateList()
    val selectedMajorList: List<String>
        get() = _selectedMajorList


    private val _selectedJobList = mutableListOf<String>().toMutableStateList()
    val selectedJobList: List<String>
        get() = _selectedJobList

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /*    fun loadMentiList() {
            _uiState.update {
                it -> it.copy(mentiList = DataSource().loadMenties())
            }
        }*/


    /**
     * 선택 직무 리스트 업데이트
     */
    fun updateChosenJobList(job: String) {
        val tempList: MutableList<String> = _uiState.value.chosenJobList
        tempList.add(job)

        _uiState.update { currentState ->
            currentState.copy(
                chosenJobList = tempList
            )
        }
    }

    /**
     * 선택 학과 리스트 업데이트
     */
    fun updateChosenMajorList(major: String) {
        val tempList: MutableList<String> = _uiState.value.chosenMajorList
        tempList.add(major)

        _uiState.update { currentState ->
            currentState.copy(
                chosenMajorList = tempList
            )
        }
    }

    /**
     * 리스트 전체 지우기
     */
    fun removeAll(list: List<String>) {
        if(list == selectedMajorList) {
            _selectedMajorList.clear()
        } else {
            _selectedJobList.clear()
        }
    }

    /**
     * 리스트 한 요소 지우기
     */
    fun remove(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.remove(item)
        } else {
            _selectedJobList.remove(item)
        }
    }

    /**
     * 리스트에 한 요소 더하기
     */
    fun add(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.add(item)
        } else {
            _selectedJobList.add(item)
        }
    }

    /**
     * 멘티 리스트 로딩
     */
    fun loadMentiList() {
        Log.d("홈통신", "loadMentiList함수 실행")

        DotoringAPI.retrofitService.searchMentee()
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("홈통신", "loadMentiList - onResponse")
                    Log.d("홈통신", "loadMentiList - response.code(): ${response.code()}")
                    Log.d("홈통신", "loadMentiList - response.body(): ${response.body()}")

                    val jsonObject = Gson().toJson(response.body())
                    Log.d("홈통신", "loadMentiList - jsonObject: $jsonObject")

                    val jo = JSONObject(jsonObject.toString())
                    Log.d("홈통신", "loadMentiList - jo: $jo")

                    val jsonObjectSuccess = jo.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("홈통신", "loadMentiList - success")

                        val responseJsonObject = jo.getJSONObject("response")
                        Log.d("홈통신", "responseJsonObject: $responseJsonObject")

                        val mentiList = responseJsonObject.optJSONArray("content")
                        Log.d("홈통신", "mentiList: $mentiList")

                        if (mentiList != null && mentiList.length() > 0) {
                            val uiMentiList: MutableList<Mentee> = mutableListOf()

                            for (i in 0 until mentiList.length()) {
                                val mentiObject = mentiList.optJSONObject(i)

                                val mentee = Mentee(
//                                    id = mentiObject.getLong("id"),
                                    nickname = mentiObject.getString("nickname"),
                                    profileImage = mentiObject.getString("profileImage"),
                                    major = mentiObject.getString("major"),
                                    job = mentiObject.getString("job"),
                                    introduction = mentiObject.getString("introduction")
                                )

                                uiMentiList.add(mentee)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(mentiList = uiMentiList)
                            }
                        }
                    } else {
                        Log.d("홈통신", "응답이 실패하거나 데이터가 없습니다.")

                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("홈통신", "통신 실패: $t")
                }
            })
    }
    fun loadJobAndMajorList() {
        Log.d("직업 학과 목록", "loadJobList 실행")

        DotoringRegisterAPI.retrofitService.getJobAndMajorList()
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("직업 학과 목록", "getJobList - onResponse")
                    Log.d("직업 학과 목록", "getJobList - response.code(): ${response.code()}")
                    Log.d("직업 학과 목록", "getJobList - response.body(): ${response.body()}")

                    val jsonObject = Gson().toJson(response.body())
                    Log.d("직업 학과 목록", "getJobList - jsonObject: $jsonObject")

                    val jo = JSONObject(jsonObject.toString())
                    Log.d("직업 학과 목록", "getJobList - jsonObject: $jo")

                    val jsonObjectSuccess = jo.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("직업 학과 목록", "getJobList - success")

                        val responseJsonObject = jo.getJSONObject("response")
                        Log.d("직업 학과 목록", "responseJsonObject: $responseJsonObject")

                        val jobs = responseJsonObject.optJSONArray("jobs")
                        Log.d("직업 학과 목록", "jobs: $jobs")

                        val majors = responseJsonObject.optJSONArray("majors")
                        Log.d("직업 학과 목록", "majors: $majors")

                        if (jobs != null && jobs.length() > 0) {
                            val uiJobList: MutableList<String> = mutableListOf()

                            for (i in 0 until jobs.length()) {
                                val jobString = jobs.optString(i)

                                uiJobList.add(jobString)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(optionJobList = uiJobList)
                            }
                        }

                        if (majors != null && majors.length() > 0) {
                            val uiMajorList: MutableList<String> = mutableListOf()

                            for (i in 0 until majors.length()) {
                                val majorString = majors.optString(i)

                                uiMajorList.add(majorString)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(optionMajorList = uiMajorList)
                            }
                        }
                    } else {
                        Log.d("작업 학과 목록", "응답이 실패하거나 데이터가 없습니다.")
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("작업 학과 목록", "통신 실패: $t")
                }
            })
    }

    /**
     * 학과 선택한 경우 멘티 리스트 업데이트
     */
    fun loadMentiListWithMajors() {
        Log.d("홈통신", "loadMentiList함수 실행")

        DotoringAPI.retrofitService.searchMenteeWithMajors(majors = selectedMajorList[0])
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("홈통신", "loadMentiList - onResponse")
                    Log.d("홈통신", "loadMentiList - response.code(): ${response.code()}")
                    Log.d("홈통신", "loadMentiList - response.body(): ${response.body()}")

                    val jsonObject = Gson().toJson(response.body())
                    Log.d("홈통신", "loadMentiList - jsonObject: $jsonObject")

                    val jo = JSONObject(jsonObject.toString())
                    Log.d("홈통신", "loadMentiList - jo: $jo")

                    val jsonObjectSuccess = jo.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("홈통신", "loadMentiList - success")

                        val responseJsonObject = jo.getJSONObject("response")
                        Log.d("홈통신", "responseJsonObject: $responseJsonObject")

                        val mentiList = responseJsonObject.optJSONArray("content")
                        Log.d("홈통신", "mentiList: $mentiList")

                        if (mentiList != null && mentiList.length() > 0) {
                            val uiMentiList: MutableList<Mentee> = mutableListOf()

                            for (i in 0 until mentiList.length()) {
                                val mentiObject = mentiList.optJSONObject(i)

                                val mentee = Mentee(
//                                    id = mentiObject.getLong("id"),
                                    nickname = mentiObject.getString("nickname"),
                                    profileImage = mentiObject.getString("profileImage"),
                                    major = mentiObject.getString("major"),
                                    job = mentiObject.getString("job"),
                                    introduction = mentiObject.getString("introduction")
                                )

                                uiMentiList.add(mentee)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(mentiList = uiMentiList)
                            }
                        }
                    } else {
                        Log.d("홈통신", "응답이 실패하거나 데이터가 없습니다.")

                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("홈통신", "통신 실패: $t")
                }
            })
    }
}