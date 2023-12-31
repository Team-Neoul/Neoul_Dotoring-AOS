package com.example.dotoring.ui.register.sixth

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.example.dotoring.dto.CommonResponse
import com.example.dotoring.dto.register.EmailCertificationRequest
import com.example.dotoring.dto.register.IdValidationRequest
import com.example.dotoring.network.DotoringRegisterAPI
import com.example.dotoring.ui.util.register.MentorInformation
import android.os.CountDownTimer
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import com.example.dotoring.ui.util.register.MenteeInformation
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.toImmutableList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RegisterSixthViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(RegisterSixthUiState())
    val uiState: StateFlow<RegisterSixthUiState> = _uiState.asStateFlow()


    /**
     *  ID와 관련된 함수
     */
    fun updateUserId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(memberId = idInput)
        }
    }

    fun updateIdConditionState(isIdConditionSatisfied: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isIdConditionSatisfied = isIdConditionSatisfied)
        }
    }

    fun updateIdValidationState(isIdAvailable: IdDuplicationCheckState) {
        _uiState.update { currentState ->
            currentState.copy(isIdAvailable = isIdAvailable)
        }

        updateToLoginButtonState()
    }

    /**
     *  Password와 관련된 함수
     */
    fun updateUserPassword(passwordInput: String) {
        _uiState.update { currentState ->
            currentState.copy(password = passwordInput)
        }
    }

    fun updatePasswordConditionState(isPasswordConditionSatisfied: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isPasswordConditionSatisfied = isPasswordConditionSatisfied)
        }

        updateToLoginButtonState()
    }

    fun updatePasswordCertification(pwCertificationInput: String) {
        _uiState.update { currentState ->
            currentState.copy(passwordCertification = pwCertificationInput)
        }
    }

    fun passwordErrorCheck() {
        if (uiState.value.password == uiState.value.passwordCertification) {
            _uiState.update { currentState ->
                currentState.copy(isPasswordCertified = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(isPasswordCertified = false)
            }
        }

        updateToLoginButtonState()
    }

    /**
     *  Email과 관련된 함수
     */
    fun updateEmail(emailInput: String) {

//        val emailInput = emailInput.replace("@", "%40")

        _uiState.update { currentState ->
            currentState.copy(email = emailInput)
        }
    }

    fun updateEmailConditionState(isEmailConditionSatisfied: Boolean) {
        Log.d(
            "테스트 - 이메일 조건 검증 확인",
            "updateEmailConditionState - isEmailConditionSatisfied: $isEmailConditionSatisfied"
        )

        _uiState.update { currentState ->
            currentState.copy(
                isEmailConditionSatisfied = isEmailConditionSatisfied,
            )
        }
    }

    /**
     *  Email 인증 코드와 관련된 함수
     */
    fun updateValidationCode(codeInput: String) {
        _uiState.update { currentState ->
            currentState.copy(validationCode = codeInput)
        }
    }

    fun updateCodeState(codeState: CodeState) {
        _uiState.update { currentState ->
            currentState.copy(codeState = codeState)
        }
    }

    private fun updateToLoginButtonState() {
        Log.d(
            "로그인 하러 가기 버튼 상태",
            "updateToLoginButtonState - uiState.value.isToLoginButtonEnabled: ${uiState.value.isToLoginButtonEnabled}"
        )
        Log.d(
            "로그인 하러 가기 버튼 상태",
            "updateToLoginButtonState - uiState.value.isIdAvailable: ${uiState.value.isIdAvailable}"
        )
        Log.d(
            "로그인 하러 가기 버튼 상태",
            "updateToLoginButtonState - uiState.value.isPasswordConditionSatisfied: ${uiState.value.isPasswordConditionSatisfied}"
        )
        Log.d(
            "로그인 하러 가기 버튼 상태",
            "updateToLoginButtonState - uiState.value.isPasswordCertified: ${uiState.value.isPasswordCertified}"
        )
        Log.d(
            "로그인 하러 가기 버튼 상태",
            "updateToLoginButtonState - uiState.value.emailValidationState: ${uiState.value.emailValidationState}"
        )

        if (uiState.value.emailValidationState == EmailValidationState.Valid
            && uiState.value.isPasswordCertified
            && uiState.value.isPasswordConditionSatisfied
            && uiState.value.isIdAvailable == IdDuplicationCheckState.DuplicationCheckSuccess
        ) {
            _uiState.update { currentState ->
                currentState.copy(isToLoginButtonEnabled = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(isToLoginButtonEnabled = false)
            }
        }
    }

    fun updateEmailValidationState(emailValidationState: EmailValidationState) {
        _uiState.update { currentState ->
            currentState.copy(emailValidationState = emailValidationState)
        }

        updateToLoginButtonState()
    }

    fun updateEmailValidationButtonState(isValidationButtonEnabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isValidationButtonEnabled = isValidationButtonEnabled)
        }
    }

    fun startTimer(timer: CountDownTimer) {
        updateCodeState(CodeState.Valid)
        updateEmailValidationButtonState(true)

        timer.start()
    }

    fun getTimer(): CountDownTimer {
        return object : CountDownTimer(300000, 1000) {

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
                    currentState.copy(certificationPeriod = "인증 시간 초과")
                }
                updateCodeState(CodeState.Expired)
                updateEmailValidationButtonState(false)
            }
        }
    }

    fun userIdDuplicationCheck() {
        val idValidationRequest = IdValidationRequest(loginId = uiState.value.memberId)
        val idValidationRequestCall: Call<CommonResponse> =
            DotoringRegisterAPI.retrofitService.loginIdValidation(idValidationRequest)

        idValidationRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if (response.isSuccessful) {
                    updateIdValidationState(IdDuplicationCheckState.DuplicationCheckSuccess)

                } else {
                    updateIdValidationState(IdDuplicationCheckState.DuplicationCheckFail)
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

    fun sendAuthenticationCode() {
        val email = uiState.value.email
        val authenticationCodeRequestCall: Call<CommonResponse> =
            DotoringRegisterAPI.retrofitService.sendAuthenticationCode(email = uiState.value.email)

        authenticationCodeRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if (response.isSuccessful) {
                    val json = Gson().toJson(response.body())
                    val jsonObject = JSONObject(json)
                    val jsonObjectSuccess = jsonObject.getBoolean("success")
                    val response = jsonObject.getJSONObject("response")
                    val emailVerificationCode = response.optJSONObject("emailVerificationCode")
                    val emailVerificationCodeToString = emailVerificationCode?.toString() ?: ""

                    _uiState.update { currentState ->
                        currentState.copy(validationCode = emailVerificationCodeToString)
                    }
                } else {
                    updateEmailValidationState(EmailValidationState.AlreadySigned)
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

    fun codeCertification() {
        val codeCertificationRequest = EmailCertificationRequest(
            emailVerificationCode = uiState.value.validationCode,
            email = uiState.value.email
        )
        val codeCertificationRequestCall: Call<CommonResponse> =
            DotoringRegisterAPI.retrofitService.emailCertification(codeCertificationRequest)

        codeCertificationRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if (response.isSuccessful) {
                    updateEmailValidationState(EmailValidationState.Valid)
                } else {
                    updateEmailValidationState(EmailValidationState.CodeInvalid)
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

    fun mentorRegister(mentorInformation: MentorInformation) {
        val certifications: MutableList<MultipartBody.Part> = mutableListOf<MultipartBody.Part>()

        if (mentorInformation.employmentCertification != null) {
            certifications.add(makePart(mentorInformation.employmentCertification))
        }
        if (mentorInformation.graduateCertification != null) {
            certifications.add(makePart(mentorInformation.graduateCertification))
        }

        certifications.toImmutableList()

        val school: RequestBody = mentorInformation.company.toRequestBody()
        val grade: Int = mentorInformation.careerLevel
        val fields: RequestBody = mentorInformation.field[0].toRequestBody()
        val majors: RequestBody = mentorInformation.major[0].toRequestBody()
        val nickname: RequestBody = mentorInformation.nickname.toRequestBody()
        val introduction: RequestBody = mentorInformation.introduction.toRequestBody()
        val loginId: RequestBody = uiState.value.memberId.toRequestBody()
        val password: RequestBody = uiState.value.password.toRequestBody()
        val email: RequestBody = uiState.value.email.toRequestBody()

        val finalRegisterRequestCall: Call<CommonResponse> =
            DotoringRegisterAPI.retrofitService.signUpAsMentor(
                school = school,
                grade = grade,
                fields = fields,
                majors = majors,
                nickname = nickname,
                introduction = introduction,
                loginId = loginId,
                password = password,
                email = email,
                certifications = certifications
            )

        finalRegisterRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if (response.isSuccessful) {
                    println("멘토님, 성공적으로 회원 가입이 완료되었습니다.")
                } else {
                    println("회원 가입에 실패했습니다.")
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

    fun menteeRegister(menteeInformation: MenteeInformation) {
        Log.d("통신 - 로그인 하기", "menteeRegister - 통신 시작")
        val certifications: MutableList<MultipartBody.Part> = mutableListOf<MultipartBody.Part>()

        if (menteeInformation.enrollmentCertification != null) {
            certifications.add(makePart(menteeInformation.enrollmentCertification))
        }

        certifications.toImmutableList()
        Log.d("통신 - 로그인 하기", "menteeRegister - certifications: $certifications")
        Log.d("통신 - 로그인 하기", "menteeRegister - menteeInformation: ${menteeInformation.toString()}")

        Log.d(
            "통신 - 로그인 하기",
            "menteeRegister - menteeInformation.fields: ${menteeInformation.field}"
        )
        Log.d("통신 - 로그인 하기", "menteeRegister - menteeInformation.major: ${menteeInformation.major}")

        val school: RequestBody = menteeInformation.school.toRequestBody()
        val grade: Int = menteeInformation.grade
        val fields: RequestBody = menteeInformation.field[0].toRequestBody()
        val majors: RequestBody = menteeInformation.major[0].toRequestBody()
        val nickname: RequestBody = menteeInformation.nickname.toRequestBody()
        val introduction: RequestBody = menteeInformation.introduction.toRequestBody()
        val loginId: RequestBody = uiState.value.memberId.toRequestBody()
        val password: RequestBody = uiState.value.password.toRequestBody()
        val email: RequestBody = uiState.value.email.toRequestBody()

        val finalRegisterRequestCall: Call<CommonResponse> =
            DotoringRegisterAPI.retrofitService.signUpAsMentee(
                school = school,
                grade = grade,
                fields = fields,
                majors = majors,
                nickname = nickname,
                introduction = introduction,
                loginId = loginId,
                password = password,
                email = email,
                certifications = certifications
            )

        Log.d("통신 - 로그인 하기", "menteeRegister - finalRegisterRequestCall: $finalRegisterRequestCall")

        finalRegisterRequestCall.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                if (response.isSuccessful) {
                    println("멘티님, 성공적으로 회원 가입이 완료되었습니다.")
                } else {
                    println("회원 가입에 실패했습니다.")
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

    private fun makePart(uri: Uri): MultipartBody.Part {
        val imageFile = File(createCopyAndReturnRealPath(uri, getApplication()))
        Log.d("파일 올리기", "absolutelyPath - imageFile: $imageFile")

        val requestBody: RequestBody = imageFile.asRequestBody("image/*".toMediaType())

        return MultipartBody.Part.createFormData(
            "certifications",
            imageFile.name,
            requestBody
        )
    }

    /**
     * 파일을 절대 경로로 변환하는 함수
     */
    @SuppressLint("Range")
    private fun absolutelyPath(path: Uri, context: Context): String {
//        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(path, null, null, null, null)
        Log.d("파일 올리기", "absolutelyPath - c: $cursor")
//        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//        Log.d("absolutelyPath", "absolutelyPath - index: $index")

        cursor?.moveToNext()

        val result = cursor?.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        Log.d("파일 올리기", "absolutelyPath - result: $result")

        cursor?.close()

        return result!!
    }

    // 절대경로 파악할 때 사용된 메소드
    private fun createCopyAndReturnRealPath(uri: Uri, context: Context): String? {
        val contentResolver: ContentResolver = context.contentResolver

        val mimeType: String? = contentResolver.getType(uri)


        // 파일 경로를 만듬
        val filePath: String = (context.applicationInfo.dataDir + File.separator
                + System.currentTimeMillis() + ".png")
        Log.d("파일 경로", "createCopyAndReturnRealPath - filePath: $filePath")

        val file = File(filePath)
        Log.d("파일 경로", "createCopyAndReturnRealPath - file: $file")
        Log.d(
            "파일 경로",
            "createCopyAndReturnRealPath - contentResolver.getType(file.toUri()): ${
                contentResolver.getType(file.toUri())
            }"
        )


        val bytes = contentResolver.openInputStream(uri).use {
            it?.readBytes()
        }
        Log.d("파일 경로", "createCopyAndReturnRealPath - bytes?.size: ${bytes?.size}")

        FileOutputStream(file).use {
            it.write(bytes)
        }
        Log.d("파일 경로", "createCopyAndReturnRealPath - file.toURI(): ${file.toURI()}")
        println(file.toUri())

        return file.absolutePath
    }


}



