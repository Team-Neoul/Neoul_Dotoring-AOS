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


    private val _selectedJobList = mutableListOf<String>().toMutableStateList()
    val selectedJobList: List<String>
        get() = _selectedJobList

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
            _selectedJobList.clear()
        }
    }

    fun remove(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.remove(item)
        } else {
            _selectedJobList.remove(item)
        }
    }

    fun add(list: List<String>, item: String) {
        if(list == selectedMajorList) {
            _selectedMajorList.add(item)
        } else {
            _selectedJobList.add(item)
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

    fun updateUserJob(userJob: String) {
        _uiState.update { currentState ->
            currentState.copy(job = userJob)
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

    fun updateChosenJobList(job: String) {
        val tempList: MutableList<String> = _uiState.value.chosenJobList
        tempList.add(job)

        _uiState.update { currentState ->
            currentState.copy(
                chosenJobList = tempList
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
}