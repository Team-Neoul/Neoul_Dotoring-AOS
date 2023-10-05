package com.example.dotoring_neoul.ui.register.sixthpackage

import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.register.EmailCertificationRequest
import com.example.dotoring_neoul.dto.register.IdValidationRequest
import com.example.dotoring_neoul.dto.register.SaveMentoRqDTO
import com.example.dotoring_neoul.network.DotoringRegisterAPI
import com.example.dotoring_neoul.ui.register.sixth.RegisterSixthUiState
import com.example.dotoring_neoul.ui.util.register.MentoInformation
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RegisterSixthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(RegisterSixthUiState())
    val uiState: StateFlow<RegisterSixthUiState> = _uiState.asStateFlow()

    fun updateUserId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(memberId = idInput)
        }
    }

    fun toggleErrorTextColor() {
        if( _uiState.value.idError ) {
            _uiState.update { currentState ->
                currentState.copy(idErrorTextColor = Color(0xffff7B7B))
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(idErrorTextColor = Color.Transparent)
            }
        }
    }

    fun updateUserPassword(passwordInput: String) {
        _uiState.update { currentState ->
            currentState.copy(password = passwordInput)
        }
    }

    fun updatePasswordCertification(pwCertificationInput: String) {
        _uiState.update { currentState ->
            currentState.copy(passwordCertification = pwCertificationInput)
        }
    }

    fun passwordErrorCheck() {
        if (_uiState.value.password == _uiState.value.passwordCertification) {
            _uiState.update { currentState ->
                currentState.copy(passwordCertified = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(passwordCertified = false)
            }
        }

        if( _uiState.value.passwordCertified ) {
            _uiState.update { currentState ->
                currentState.copy(passwordErrorTextColor = Color.Transparent)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(passwordErrorTextColor = Color(0xffff7B7B))
            }
        }
    }

    fun updateEmail(emailInput: String) {

//        val emailInput = emailInput.replace("@", "%40")

        _uiState.update { currentState ->
            currentState.copy(email = emailInput)
        }
    }

    fun updateValidationCode (codeInput: String) {
        _uiState.update { currentState ->
            currentState.copy(validationCode = codeInput)
        }
    }

    fun toggleEmailErrorTextColor() {
        if( _uiState.value.emailValidated ) {
            _uiState.update { currentState ->
                currentState.copy(emailErrorTextColor = Color.Transparent)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(emailErrorTextColor = Color(0xffff7B7B))
            }
        }
    }

    fun updateBtnState () {
        _uiState.update { currentState ->
            currentState.copy(btnState = true)
        }
    }

    fun startTimer () {
        object : CountDownTimer(300000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = millisUntilFinished / 1000
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val format = "$minutes:$seconds"

                _uiState.update { currentState ->
                    currentState.copy(certificationPeriod = format)
                }
            }

            override fun onFinish() {
                _uiState.update { currentState ->
                    currentState.copy(certificationPeriod = "시간 초과")
                }
            }
        }.start()
    }

    fun userIdDuplicationCheck() {
        Log.d("통신", "userIdDuplicationCheck - 시작")

        val idValidationRequest = IdValidationRequest(loginId = uiState.value.memberId)
        Log.d("통신", "userIdDuplicationCheck - $idValidationRequest")

        val idValidationRequestCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.loginIdValidation(idValidationRequest)

        idValidationRequestCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                Log.d("통신", "userIdDuplicationCheck - onResponse")
                Log.d("통신", "userIdDuplicationCheck - response.code(): ${response.code()}")

                val jsonObject = Gson().toJson(response.body())
                Log.d("통신", "userIdDuplicationCheck - jsonObject: $jsonObject")

                val jo = JSONObject(jsonObject)
                val jsonObjectSuccess = jo.getBoolean("success")

                if (jsonObjectSuccess) {
                    Log.d("통신", "userIdDuplicationCheck - success")

                    _uiState.update { currentState ->
                        currentState.copy(idAvailable = true)
                    }
                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $idValidationRequestCall")
            }
        })
    }

    fun sendAuthenticationCode() {
        val email = uiState.value.email
        Log.d("통신", "sendAuthenticationCode - 시작")
        Log.d("통신", "sendAuthenticationCode - $email")

        val authenticationCodeRequestCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.sendAuthenticationCode(email = uiState.value.email)

        authenticationCodeRequestCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                Log.d("통신", "sendAuthenticationCode - onResponse")
                Log.d("통신", "sendAuthenticationCode - response.body() : ${response.body()}")
                Log.d("통신", "sendAuthenticationCode - response.code() : ${response.code()}")

                val jsonObject = Gson().toJson(response.body())
                val jo = JSONObject(jsonObject)
                val jsonObjectSuccess = jo.getBoolean("success")

                if (jsonObjectSuccess) {

                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $authenticationCodeRequestCall")
            }
        })
    }

    fun codeCertification() {
        Log.d("통신", "codeCertification - 시작")

        val codeCertificationRequest = EmailCertificationRequest(emailVerificationCode = uiState.value.validationCode, email = uiState.value.email)
        Log.d("통신", "codeCertification - Request: $codeCertificationRequest")

        val codeCertificationRequestCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.emailCertification(codeCertificationRequest)

        codeCertificationRequestCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                Log.d("통신", "codeCertification - onResponse")
                Log.d("통신", "codeCertification - response.body() : ${response.body()}")
                Log.d("통신", "codeCertification - response.code() : ${response.code()}")

                val jsonObject = Gson().toJson(response.body())
                val jo = JSONObject(jsonObject)
                val jsonObjectSuccess = jo.getBoolean("success")

                if (jsonObjectSuccess) {
                    _uiState.update { currentState ->
                        currentState.copy(emailValidated = true)
                    }

                    toggleEmailErrorTextColor()
                    updateBtnState()

                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $codeCertificationRequestCall")
            }
        })
    }

    private fun makePart(uri: Uri?, fileName: String): MultipartBody.Part {
        val path : String = Environment.getExternalStorageDirectory().absolutePath + "/AP/"
        val dir : File = File(path)

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val fullName = path + "fileName"
        val file : File = File(fullName)


//        val image = File.createTempFile(fileName, ".pdf", null)
//        val destinationUri = Uri.fromFile(image)
//
//        val requestFile = file.asRequestBody("application/pdf".toMediaTypeOrNull())


//        val requestFile = Request.Builder()
//            .post(file.asRequestBody())
//            .build()

//        val filePath = uri?.path
//        val imageFile = File(filePath).createNewFile()
//        val requestFile : RequestBody = RequestBody.create(
//            IMAGE_TYPE_JPEG.toMediaTypeOrNull(), imageFile
//        )
        val fileBody = uri!!.path?.let { File(it) }
        var requestBody : RequestBody = fileBody!!.asRequestBody("*/*".toMediaType())
        var body : MultipartBody.Part = MultipartBody.Part.createFormData("uploaded_file", fileName, requestBody)
//        val requestFile = file.asRequestBody("*/*".toMediaType())
//
//        return MultipartBody.Part.createFormData("file", file.name, requestFile)

        return body
    }

    fun finalRegistser(mentoInformation: MentoInformation) {
        Log.d("통신", "finalRegister - 통신 시작")

        /* val employmentCertification = makePart(mentoInformation.employmentCertification, "doc_${mentoInformation.nickname}_employment.pdf")
         val graduateCertification = makePart(mentoInformation.graduateCertification, "doc_${mentoInformation.nickname}_graduation.pdf")

         val certifications = listOf(
             employmentCertification,
             graduateCertification
         )

         Log.d("통신", "finalRegister - certifications: $certifications")

         val company : RequestBody = FormBody.Builder()
             .add("company", mentoInformation.company)
             .build()

         val careerLevel : RequestBody = FormBody.Builder()
             .add("careerLevel", mentoInformation.careerLevel.toString())
             .build()

         val job : RequestBody = FormBody.Builder()
             .add("job", mentoInformation.job)
             .build()

         val major : RequestBody = FormBody.Builder()
             .add("major", mentoInformation.major)
             .build()

         val introduction : RequestBody = FormBody.Builder()
             .add("introduction", mentoInformation.introduction)
             .build()

         val loginId : RequestBody = FormBody.Builder()
             .add("loginId", uiState.value.memberId)
             .build()

         val password : RequestBody = FormBody.Builder()
             .add("password", uiState.value.password)
             .build()

         val email : RequestBody = FormBody.Builder()
             .add("email", uiState.value.email)
             .build()

         val map = hashMapOf<String, RequestBody>()
         map["company"] = company
         map["careerLevel"] = careerLevel
         map["job"] = job
         map["major"] = major
         map["introduction"] = introduction
         map["loginId"] = loginId
         map["password"] = password
         map["email"] = email

         val mentoSignupRequestDTO = mutableMapOf<String, HashMap<String, RequestBody>>()
         mentoSignupRequestDTO["mentoSignupRequestDTO"] = map*/

//        val requestBody : RequestBody = MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("company", mentoInformation.company)
//                .addFormDataPart("careerLevel", mentoInformation.careerLevel.toString())
//                .addFormDataPart("job", mentoInformation.job)
//                .addFormDataPart("major", mentoInformation.major)
//                .addFormDataPart("introduction", mentoInformation.introduction)
//                .addFormDataPart("loginId", uiState.value.memberId)
//                .addFormDataPart("password", uiState.value.password)
//                .addFormDataPart("email", uiState.value.email)
//                .build()
//        Log.d("통신", "finalRegister - mentoRequestDTO : $mentoSignupRequestDTO")


        val saveMentoRqDTO: SaveMentoRqDTO = SaveMentoRqDTO(
            company = mentoInformation.company,
            careerLevel = mentoInformation.careerLevel,
            job = "경영",
            major = "경영학부",
            nickname = mentoInformation.nickname,
            introduction = mentoInformation.introduction,
            loginId = uiState.value.memberId,
            password = uiState.value.password,
            email = uiState.value.email
        )

        val finalRegisterRequestCall: Call<CommonResponse> = DotoringRegisterAPI.retrofitService.signUpAsMento(
            /*certifications = certifications,
            mentoSignupRequestDTO = mentoSignupRequestDTO*/
            mentoSingupRequest = saveMentoRqDTO
        )

        Log.d("통신", "finalRegister - finalRegisterRequestCall: $finalRegisterRequestCall")


        finalRegisterRequestCall.enqueue(object: Callback<CommonResponse> {
            override fun onResponse(call: Call<CommonResponse>, response: Response<CommonResponse>) {
                Log.d("통신", "finalRegister - onResponse")
                Log.d("통신", "finalRegister - response.body() : ${response.body()}")
                Log.d("통신", "finalRegister - response.code() : ${response.code()}")

                val jsonObject = Gson().toJson(response.body())
                val jo = JSONObject(jsonObject)
                val jsonObjectSuccess = jo.getBoolean("success")

                if (jsonObjectSuccess) {

                }
            }
            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                Log.d("통신", "통신 실패: $t")
                Log.d("회원 가입 통신", "요청 내용 - $finalRegisterRequestCall")
            }
        })
    }
}