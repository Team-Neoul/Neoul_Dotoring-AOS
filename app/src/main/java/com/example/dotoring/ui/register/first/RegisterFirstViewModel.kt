package com.example.dotoring.ui.register.first


import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.network.DotoringRegisterAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.viewModelScope
import com.example.dotoring.network.DotoringAPI
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterFirstViewModel() : ViewModel() {

    private val _selectedMajorList = mutableListOf<String>().toMutableStateList()
    val selectedMajorList: List<String>
        get() = _selectedMajorList


    private val _selectedFieldList = mutableListOf<String>().toMutableStateList()
    val selectedFieldList: List<String>
        get() = _selectedFieldList

    private val _uiState = MutableStateFlow(RegisterFirstUiState())
    val uiState: StateFlow<RegisterFirstUiState> = _uiState.asStateFlow()

    private var _isSnackbarShowing = MutableStateFlow(false)
    val isSnackbarShowing = _isSnackbarShowing.asStateFlow()
    private fun showSnackBar() {
        viewModelScope.launch {
            _isSnackbarShowing.emit(true)
        }
    }

    private fun updateSnackbarMessage(snackbarMessage: String) {
        _uiState.update { currentState ->
            currentState.copy(snackbarMessage = snackbarMessage)
        }
    }

    private fun hideSnackBar() {
        _isSnackbarShowing.value = false
    }

    /**
     * 화면에 선택한 학과와 멘토링 분야 리스트를 보여주기 위해 String으로 변환하는 함수
     *
     * @param List<String> list - 선택한 학과 혹은 멘토링 분야의 리스트
     */
    fun toString(list: List<String>): String {
        var rString = ""
        list.forEach {
            rString += "$it, "
        }
        if (rString.isNotEmpty()) {
            return rString.substring(0, rString.length - 2)
        } else {
            return ""
        }
    }

    fun removeAll(list: List<String>) {
        if (list == selectedMajorList) {
            _selectedMajorList.clear()
        } else {
            _selectedFieldList.clear()
        }
    }

    fun remove(list: List<String>, item: String) {
        if (list == selectedMajorList) {
            _selectedMajorList.remove(item)
        } else {
            _selectedFieldList.remove(item)
        }
    }

    fun add(list: List<String>, item: String) {
        if (list == selectedMajorList) {
            _selectedMajorList.add(item)
        } else {
            _selectedFieldList.add(item)
        }
    }

    fun updateUserCompany(userCompany: String) {
        _uiState.update { currentState ->
            currentState.copy(company = userCompany)
        }
    }

    fun updateUserCareer(userCareer: String) {
        _uiState.update { currentState ->
            currentState.copy(careerLevel = userCareer)
        }
    }


    fun updateChosenFieldList(fieldList: List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                chosenFieldList = fieldList.toMutableList()
            )
        }
    }

    fun updateChosenMajorList(majorList: List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                chosenMajorList = majorList.toMutableList()
            )
        }
    }

    fun updateCompanyFieldState() {
        if (uiState.value.company == "") {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCompanyField = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillCompanyField: ${uiState.value.fillCompanyField}")
    }

    fun updateCareerFieldState() {
        if (uiState.value.careerLevel == "") {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillCareerField = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillCareerField: ${uiState.value.fillCareerField}")
    }

    fun updateFieldFieldState() {
        if (uiState.value.chosenFieldList.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillJobField = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillJobField: ${uiState.value.fillJobField}")
    }


    fun updateMajorFieldState() {
        if (uiState.value.chosenMajorList.isEmpty()) {
            _uiState.update { currentState ->
                currentState.copy(fillMajorField = false)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(fillMajorField = true)
            }
        }
        Log.d("다음 버튼 활성화 함수", "uiState.value.fillMajorField: ${uiState.value.fillMajorField}")
    }

    fun enableNextButton() {
        if (uiState.value.fillCompanyField && uiState.value.fillCareerField && uiState.value.fillJobField && uiState.value.fillMajorField) {
            _uiState.update { currentState ->
                currentState.copy(firstBtnState = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(firstBtnState = false)
            }
        }
        Log.d("다음 버튼 활성화 함수", "registerFirstUiState.firstBtnState: ${uiState.value.firstBtnState}")

    }

    fun loadFieldList() {
        DotoringRegisterAPI.retrofitService.getFieldList()
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
                                currentState.copy(optionFieldList = uiFieldList)
                            }
                        }
                    } else {
                        val errorResponse = DotoringAPI.getErrorResponse(response.errorBody()!!)
                        val json = Gson().toJson(errorResponse)
                        val jsonObject = JSONObject(json)
                        val jsonObjectError = jsonObject.getJSONObject("error")
                        val errorCode = jsonObjectError.getString("message")
                        showSnackBar()
                        updateSnackbarMessage(errorCode)
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "인터넷 연결이 끊겼습니다."
                        is HttpException -> "알 수 없는 오류가 발생했어요."
                        else -> t.localizedMessage
                    }
                    showSnackBar()
                    updateSnackbarMessage(errorMessage)
                }
            })
    }

    fun loadMajorList() {
        DotoringRegisterAPI.retrofitService.getMajorList()
            .enqueue(object : Callback<CommonResponse> {

                override fun onResponse(
                    call: Call<CommonResponse>,
                    response: Response<CommonResponse>
                ) {
                    if (response.isSuccessful) {
                        val json = Gson().toJson(response.body())
                        val jsonObject = JSONObject(json.toString())

                        val responseJsonObject = jsonObject.getJSONObject("response")
                        val majors = responseJsonObject.optJSONArray("majors")

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
                        val errorResponse = DotoringAPI.getErrorResponse(response.errorBody()!!)
                        val json = Gson().toJson(errorResponse)
                        val jsonObject = JSONObject(json)
                        val jsonObjectError = jsonObject.getJSONObject("error")
                        val errorMessage = jsonObjectError.getString("message")
                        showSnackBar()
                        updateSnackbarMessage(errorMessage)
                    }
                }

                override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                    val errorMessage = when (t) {
                        is IOException -> "인터넷 연결이 끊겼습니다."
                        is HttpException -> "알 수 없는 오류가 발생했어요."
                        else -> t.localizedMessage
                    }
                    showSnackBar()
                    updateSnackbarMessage(errorMessage)
                }
            })
    }
}