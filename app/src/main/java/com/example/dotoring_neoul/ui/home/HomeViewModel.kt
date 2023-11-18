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

class HomeViewModel : ViewModel() {
    private val _selectedMajorList = mutableListOf<String>().toMutableStateList()
    val selectedMajorList: List<String>
        get() = _selectedMajorList


    private val _selectedFieldList = mutableListOf<String>().toMutableStateList()
    val selectedFieldList: List<String>
        get() = _selectedFieldList

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var _lastMember = false
    val lastMember: Boolean
        get() = _lastMember

    private var lastMemberId: Int? = null

    fun updateUserNickname(nickname: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nickname = nickname
            )
        }
    }

    /**
     * 선택한 멘토링 분야 리스트를 업데이트
     *
     * @param List<String> fieldList - 선택한 멘토링 분야의 리스트
     */
    fun updateChosenFieldList(fieldList: List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                chosenFieldList = fieldList.toMutableList()
            )
        }
    }

    /**
     * 선택한 학과 리스트를 업데이트
     *
     * @param List<String> majorList - 선택한 학과의 리스트
     */
    fun updateChosenMajorList(majorList: List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                chosenMajorList = majorList.toMutableList()
            )
        }
    }

    /**
     * BottomSheet에서 초기화 버튼을 눌렀을 때, 학과나 멘토링 분야 리스트 전체를 지우는 함수
     *
     * @param List<String> list - 선택한 학과나 멘토링 분야의 리스트
     */
    fun removeAll(list: List<String>) {
        if (list == selectedMajorList) {
            _selectedMajorList.clear()
        } else {
            _selectedFieldList.clear()
        }
    }

    /**
     * BottomSheet에서 선택된 직무 혹은 멘토링 분야에서 [X] 버튼을 눌렀을 때, 해당 값 삭제
     *
     * @param List<String> list - 선택한 학과 혹은 멘토링 분야의 리스트
     * @param String item - 리스트에서 삭제하고자 하는 하나의 데이터
     */
    fun remove(list: List<String>, item: String) {
        if (list == selectedMajorList) {
            _selectedMajorList.remove(item)
        } else {
            _selectedFieldList.remove(item)
        }
    }

    /**
     * 학과나 멘토링 분야 리스트에 데이터 추가하기
     *
     * @param List<String> list - 학과나 멘토링 분야의 전체 리스트
     * @param String item - 선택 리스트에 추가할 하나의 데이터
     */
    fun add(list: List<String>, item: String) {
        if (list == selectedMajorList) {
            _selectedMajorList.add(item)
        } else {
            _selectedFieldList.add(item)
        }
    }


    /**
     * 학과 필터 상태를 업데이트 하는 함수
     */
    fun updateMajorFieldState() {
        if (uiState.value.chosenMajorList.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(hasChosenMajor = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(hasChosenMajor = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillMajorField: ${uiState.value.hasChosenMajor}")
    }

    /**
     * 멘토링 분야 필터 상태를 업데이트 하는 함수
     */
    fun updateFieldFieldState() {
        if (uiState.value.chosenFieldList.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(hasChosenField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(hasChosenField = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillJobField: ${uiState.value.hasChosenField}")
    }

    /**
     * 멘티 리스트 로딩
     */
    fun loadMenteeList() {
        Log.d("멘티 리스트 로드", "loadMenteeList함수 실행")

        DotoringAPI.retrofitService.getMentee(
            size = 4,
            lastMentiId = lastMemberId
        )
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    Log.d("멘티 리스트 로드", "loadMenteeList - onResponse")
                    Log.d("멘티 리스트 로드", "loadMenteeList - response.code(): ${response.code()}")
                    Log.d("멘티 리스트 로드", "loadMenteeList - response.body(): ${response.body()}")

                    if (response.code() == 200) {
                        val json = Gson().toJson(response.body())
                        Log.d("멘티 리스트 로드", "loadMenteeList - json: $json")

                        val jsonObject = JSONObject(json.toString())
                        Log.d("멘티 리스트 로드", "loadMenteeList - jsonObject: $jsonObject")

                        Log.d("멘티 리스트 로드", "loadMenteeList - success")

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        Log.d("멘티 리스트 로드", "responseJsonObject: $responseJsonObject")

                        val menteeList = responseJsonObject.optJSONArray("content")
                        Log.d("멘티 리스트 로드", "menteeList: $menteeList")

                        if (menteeList != null && menteeList.length() > 0) {
                            val uiMentorList: MutableList<Member> = uiState.value.memberList.toMutableList()

                            for (i in 0 until menteeList.length()) {
                                val menteeObject = menteeList.optJSONObject(i)

                                val mentee = Member(
                                    id = menteeObject.getInt("id"),
                                    nickname = menteeObject.getString("nickname"),
                                    profileImage = menteeObject.getString("profileImage"),
                                    majors = menteeObject.getString("majors"),
                                    fields = menteeObject.getString("fields"),
                                    introduction = menteeObject.getString("introduction"),
                                )

                                uiMentorList.add(mentee)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(memberList = uiMentorList)
                            }
                        }

                        val pageable = responseJsonObject.getJSONObject("pageable")
                        val nickname = pageable.getString("nickname")
                        updateUserNickname(nickname)

                        _lastMember = responseJsonObject.getBoolean("last")
                        Log.d("멤버 리스트 로드", "_lastMember: $lastMember")
                        lastMemberId = menteeList.optJSONObject(menteeList.length() - 1).getInt("id")
                        Log.d("멤버 리스트 로드", "_lastMemberId: $lastMemberId")
                    } else {
                        Log.d("멘티 리스트 로드", "응답을 받지 못했거나, 멘티 리스트가 없습니다.")
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("멘티 리스트 로드", "통신 실패: $t")
                }
            })
    }

    /**
     * 멘토 리스트 로딩
     */
    fun loadMentorList() {
        Log.d("멘토 리스트 로드", "loadMentorList함수 실행")

        DotoringAPI.retrofitService.getMentor(
            size = 4,
            lastMentoId = lastMemberId
        )
            .enqueue(object : Callback<CommonResponse> {
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    Log.d("멘토 리스트 로드", "loadMentorList - onResponse")
                    Log.d("멘토 리스트 로드", "loadMentorList - response.code(): ${response.code()}")
                    Log.d("멘토 리스트 로드", "loadMentorList - response.body(): ${response.body()}")

                    if (response.code() == 200) {
                        val json = Gson().toJson(response.body())
                        Log.d("멘토 리스트 로드", "loadMentorList - json: $json")

                        val jsonObject = JSONObject(json.toString())
                        Log.d("멘토 리스트 로드", "loadMentorList - jsonObject: $jsonObject")

                        val jsonObjectSuccess = jsonObject.getBoolean("success")

                        if (jsonObjectSuccess) {
                            Log.d("멘토 리스트 로드", "loadMentorList - success")

                            val responseJsonObject = jsonObject.getJSONObject("response")
                            Log.d("멘토 리스트 로드", "responseJsonObject: $responseJsonObject")

                            val mentorList = responseJsonObject.optJSONArray("content")
                            Log.d("멘토 리스트 로드", "mentorList: $mentorList")

                            if (mentorList != null && mentorList.length() > 0) {
                                val uiMentorList: MutableList<Member> = uiState.value.memberList.toMutableList()

                                for (i in 0 until mentorList.length()) {
                                    val mentorObject = mentorList.optJSONObject(i)

                                    val mentor = Member(
                                        id = mentorObject.getInt("id"),
                                        nickname = mentorObject.getString("nickname"),
                                        profileImage = mentorObject.getString("profileImage"),
                                        majors = mentorObject.getString("majors"),
                                        fields = mentorObject.getString("fields"),
                                        introduction = mentorObject.getString("introduction"),
                                    )

                                    uiMentorList.add(mentor)
                                }

                                _uiState.update { currentState ->
                                    currentState.copy(memberList = uiMentorList)
                                }
                            }
                            val pageable = responseJsonObject.getJSONObject("pageable")
                            val nickname = pageable.getString("nickname")
                            updateUserNickname(nickname)

                            _lastMember = responseJsonObject.getBoolean("last")
                            Log.d("멤버 리스트 로드", "_lastMember: $lastMember")
                            lastMemberId = mentorList.optJSONObject(mentorList.length() - 1).getInt("id")
                            Log.d("멤버 리스트 로드", "_lastMemberId: $lastMemberId")
                        } else {
                            Log.d("멘토 리스트 로드", "응답이 실패했거나 데이터가 없습니다.")
                        }
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("멘토 리스트 로드", "통신 실패: $t")
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
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    Log.d("홈통신", "loadMentiList - onResponse")
                    Log.d("홈통신", "loadMentiList - response.code(): ${response.code()}")
                    Log.d("홈통신", "loadMentiList - response.body(): ${response.body()}")

                    if (response.code() == 200) {
                        val jsonObject = Gson().toJson(response.body())
                        Log.d("홈통신", "loadMentiList - jsonObject: $jsonObject")

                        val jo = JSONObject(jsonObject.toString())
                        Log.d("홈통신", "loadMentiList - jo: $jo")

                        val responseJsonObject = jo.getJSONObject("response")
                        Log.d("홈통신", "responseJsonObject: $responseJsonObject")

                        val mentiList = responseJsonObject.optJSONArray("content")
                        Log.d("홈통신", "mentiList: $mentiList")

                        if (mentiList != null && mentiList.length() > 0) {
                            val uiMentiList: MutableList<Member> = uiState.value.memberList.toMutableList()

                            for (i in 0 until mentiList.length()) {
                                val mentiObject = mentiList.optJSONObject(i)

                                val mentee = Member(
                                    id = mentiObject.getInt("id"),
                                    nickname = mentiObject.getString("nickname"),
                                    profileImage = mentiObject.getString("profileImage"),
                                    majors = mentiObject.getString("major"),
                                    fields = mentiObject.getString("job"),
                                    introduction = mentiObject.getString("introduction"),
                                )

                                uiMentiList.add(mentee)
                            }

                            _uiState.update { currentState ->
                                currentState.copy(memberList = uiMentiList)
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

    /**
     * 백엔드에서 학과 목록을 가져오기 위한 함수
     */
    fun loadMajorList() {
        Log.d("직업 학과 목록", "loadMajorList 실행")

        DotoringRegisterAPI.retrofitService.getMajorList()
            .enqueue(object : Callback<CommonResponse> {
                /**
                 * 통신 요청이 성공한 경우, 서버의 응답을 처리하기 위한 함수
                 */
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    Log.d("학과 리스트", "loadMajorList - onResponse")
                    Log.d("학과 리스트", "loadMajorList - response.code(): ${response.code()}")
                    Log.d("학과 리스트", "loadMajorList - response.body(): ${response.body()}")

                    if (response.code() == 200) {
                        Log.d("학과 리스트", "loadMajorList - success")
                        val json = Gson().toJson(response.body())
                        Log.d("학과 리스트", "loadMajorList - jsonObject: $json")

                        val jsonObject = JSONObject(json.toString())
                        Log.d("학과 리스트", "loadMajorList - jsonObject: $jsonObject")

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        Log.d("학과 리스트", "responseJsonObject: $responseJsonObject")

                        val majors = responseJsonObject.optJSONArray("majors")
                        Log.d("학과 리스트", "majors: $majors")

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
                        Log.d("학과 리스트", "응답이 실패하거나 데이터가 없습니다.")
                    }
                }

                /**
                 * 통신 요청이 실패한 경우, 실패 이유를 보여주기 위한 함수
                 */
                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("학과 리스트", "통신 실패: $t")
                }
            })
    }

    /**
     * 백엔드에서 멘토링 분야 목록을 가져오기 위한 함수
     */
    fun loadFieldList() {
        Log.d("직업 학과 목록", "loadFieldList 실행")

        DotoringRegisterAPI.retrofitService.getFieldList()
            .enqueue(object : Callback<CommonResponse> {
                /**
                 * 통신 요청이 성공한 경우, 서버의 응답을 처리하기 위한 함수
                 */
                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    Log.d("멘토링 분야 리스트", "getFieldList - onResponse")
                    Log.d("멘토링 분야 리스트", "getFieldList - response.code(): ${response.code()}")
                    Log.d("멘토링 분야 리스트", "getFieldList - response.body(): ${response.body()}")

                    if (response.code() == 200) {
                        val json = Gson().toJson(response.body())
                        Log.d("멘토링 분야 리스트", "getFieldList - jsonObject: $json")

                        val jsonObject = JSONObject(json.toString())
                        Log.d("멘토링 분야 리스트", "getFieldList - jsonObject: $jsonObject")

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        Log.d("멘토링 분야 리스트", "responseJsonObject: $responseJsonObject")

                        val fields = responseJsonObject.optJSONArray("fields")
                        Log.d("멘토링 분야 리스트", "fields: $fields")

                        if (fields != null && fields.length() > 0) {
                            val uiFieldList: MutableList<String> = mutableListOf()

                            for (i in 0 until fields.length()) {

                                val fieldString = fields.optString(i)
                                uiFieldList.add(fieldString)

                            }

                            _uiState.update { currentState ->
                                currentState.copy(optionFieldList = uiFieldList)
                            }
                            Log.d(
                                "optionFieldList",
                                "optionFieldList: ${uiState.value.optionFieldList}"
                            )
                        }
                    } else {
                        Log.d("멘토링 분야 리스트", "응답이 실패하거나 데이터가 없습니다.")
                    }
                }

                /**
                 * 통신 요청이 실패한 경우, 실패 이유를 보여주기 위한 함수
                 */
                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    Log.d("멘토링 분야 리스트", "통신 실패: $t")
                }
            })
    }
}
