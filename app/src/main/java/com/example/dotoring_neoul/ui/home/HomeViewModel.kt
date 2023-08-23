package com.example.dotoring_neoul.ui.home

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

class HomeViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    /*    fun loadMentiList() {
            _uiState.update {
                it -> it.copy(mentiList = DataSource().loadMenties())
            }
        }*/

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
}