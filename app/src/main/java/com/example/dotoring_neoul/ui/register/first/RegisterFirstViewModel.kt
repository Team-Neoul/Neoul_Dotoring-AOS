package com.example.dotoring_neoul.ui.register.first


import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.dotoring_neoul.dto.CommonResponse
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

class RegisterFirstViewModel(): ViewModel() {

    private val _selectedMajorList = mutableListOf<String>().toMutableStateList()
    val selectedMajorList: List<String>
        get() = _selectedMajorList


    private val _selectedFieldList = mutableListOf<String>().toMutableStateList()
    val selectedFieldList: List<String>
        get() = _selectedFieldList

    fun toString(list: List<String>): String {
        var rString: String = ""
        list.forEach {
            rString += "$it, "
        }
        if(rString.isNotEmpty()) {
            return rString.substring(0, rString.length - 2)
        } else {
            return ""
        }
    }
    fun removeAll(list: List<String>) {
        if(list == selectedMajorList) {
            _selectedMajorList.clear()
        } else {
            _selectedFieldList.clear()
        }
    }

    fun remove(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.remove(item)
        } else {
            _selectedFieldList.remove(item)
        }
    }

    fun add(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.add(item)
        } else {
            _selectedFieldList.add(item)
        }
    }

    /*fun removeAll() {
        _selectedList.removeAll()
    }*/

    private val _uiState = MutableStateFlow(RegisterFirstUiState())
    val uiState: StateFlow<RegisterFirstUiState> = _uiState.asStateFlow()

    fun updateUserCompany(userCompany: String) {
        _uiState.update { currentState ->
            currentState.copy(company = userCompany)
        }
    }

    fun updateCompanyFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = true)
            }
        }
    }

    fun updateUserCareer(userCareer: String) {
        _uiState.update { currentState ->
            currentState.copy(careerLevel = userCareer)
        }
    }

    fun updateCareerFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = true)
            }
        }
    }

    fun updateUserField(userField: String) {
        _uiState.update { currentState ->
            currentState.copy(field = userField)
        }
    }

    fun updateJobFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = true)
            }
        }
    }

    fun updateChosenFieldList(job: String) {
        val tempList: MutableList<String> = _uiState.value.chosenFieldList
        tempList.add(job)

        _uiState.update { currentState ->
            currentState.copy(
                chosenFieldList = tempList
            )
        }
    }

    fun updateUserMajor(userMajor: String) {
        _uiState.update { currentState ->
            currentState.copy(major = userMajor)
        }
    }

    fun updateMajorFieldState(emptyField: Boolean) {
        if (emptyField) {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = true)
            }
        }
    }

    fun enableNextButton() {
        _uiState.update { currentState ->
            currentState.copy(firstBtnState = true)
        }
    }

    fun updateChosenMajorList(major: String) {
        val tempList: MutableList<String> = _uiState.value.chosenMajorList
        tempList.add(major)

        _uiState.update { currentState ->
            currentState.copy(
                chosenMajorList = tempList
            )
        }
    }

    fun loadFieldList() {
        Log.d("직업 학과 목록", "loadFieldList 실행")

        DotoringRegisterAPI.retrofitService.getFieldList()
            .enqueue(object : Callback<CommonResponse> {
                /**
                * 통신 요청이 성공한 경우, 서버의 응답을 처리하기 위한 함수
                */
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("멘토링 분야 리스트", "getFieldList - onResponse")
                    Log.d("멘토링 분야 리스트", "getFieldList - response.code(): ${response.code()}")
                    Log.d("멘토링 분야 리스트", "getFieldList - response.body(): ${response.body()}")

                    val json = Gson().toJson(response.body())
                    Log.d("멘토링 분야 리스트", "getFieldList - jsonObject: $json")

                    val jsonObject = JSONObject(json.toString())
                    Log.d("멘토링 분야 리스트", "getFieldList - jsonObject: $jsonObject")

                    val jsonObjectSuccess = jsonObject.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("멘토링 분야 리스트", "getFieldList - success")

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

    fun loadMajorList() {
        Log.d("직업 학과 목록", "loadMajorList 실행")

        DotoringRegisterAPI.retrofitService.getMajorList()
            .enqueue(object : Callback<CommonResponse> {
                /**
                 * 통신 요청이 성공한 경우, 서버의 응답을 처리하기 위한 함수
                 */
                override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                    Log.d("학과 리스트", "loadMajorList - onResponse")
                    Log.d("학과 리스트", "loadMajorList - response.code(): ${response.code()}")
                    Log.d("학과 리스트", "loadMajorList - response.body(): ${response.body()}")

                    val json = Gson().toJson(response.body())
                    Log.d("학과 리스트", "loadMajorList - jsonObject: $json")

                    val jsonObject = JSONObject(json.toString())
                    Log.d("학과 리스트", "loadMajorList - jsonObject: $jsonObject")

                    val jsonObjectSuccess = jsonObject.getBoolean("success")

                    if (jsonObjectSuccess) {
                        Log.d("학과 리스트", "loadMajorList - success")

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
}