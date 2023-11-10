package com.example.dotoring_neoul.network

import com.example.dotoring_neoul.MyApplication
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.login.FindIdRequest
import com.example.dotoring_neoul.dto.login.FindPwdRequest
import com.example.dotoring_neoul.dto.login.LoginRequest
import com.example.dotoring_neoul.dto.message.MessageRequest
import com.example.dotoring_neoul.dto.register.EmailCertificationRequest
import com.example.dotoring_neoul.dto.register.EmailCodeRequest
import com.example.dotoring_neoul.dto.register.IdValidationRequest
import com.example.dotoring_neoul.dto.register.NicknameValidationRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.net.CookieManager

private const val BASE_URL =
    "http://192.168.0.60:8080/"


/**
 * Interceptor에 AppInterceptor를 적용하여 토큰 적용
 * */
val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(AppInterceptor())
    .cookieJar(JavaNetCookieJar(CookieManager()))
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

val registerClient: OkHttpClient = OkHttpClient.Builder()
    .cookieJar(JavaNetCookieJar(CookieManager()))
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

/**
 * Gson을 사용하기 위한 설정
 * */
val gson : Gson = GsonBuilder()
    .setLenient()
    .create()

/**
 * 요청에 BASE_URL 삽입하여 통신하기 위한 retrofit 설정
 * */
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()

/**
 * 요청에 BASE_URL 삽입하여 통신하기 위한 retrofit 설정
 * */
val registerRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(registerClient)
    .build()

/**
 * 토큰을 저장하기 위한 Interceptor
 * accesToken에는 헤더의 Authorization에서 받아온 accessToken 토큰을 String값으로 저장
 * refreshToken에는 헤더의 Cookie에서 받아온 refreshToken 토큰을 String값으로 저장
 * Request를 보낼 때에는 Header에 이전 요청에서 저장되었던 토큰 값들을 넣어서 요청하는 builder 이용
 * */
class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val accessToken = MyApplication.prefs.getString("Authorization", "") // ViewModel에서 지정한 key로 JWT 토큰을 가져온다.
        val refreshToken = MyApplication.prefs.getRefresh("Cookie", "")
        val newRequest = request().newBuilder()
            .addHeader("Authorization", accessToken) // 헤더에 authorization라는 key로 JWT 를 넣어준다.
            .addHeader("Cookie", refreshToken)
            .build()
        proceed(newRequest)
    }
}


interface DotoringAPIService {

    /**
     * nicknameValidation: 닉네임 중복확인을 위한 api
     * */
    @POST("api/mento/valid-nickname")
    fun mentoNicknameValidation(
        @Body nicknameValidationRequest: NicknameValidationRequest
    ): Call<CommonResponse>

    @POST("api/menti/valid-nickname")
    fun menteeNicknameValidation(
        @Body nicknameValidationRequest: NicknameValidationRequest
    ): Call<CommonResponse>

    /**
     * loginIdValidation: 아이디 중복확인을 위한 api
     * */
    @POST("api/member/valid-loginId")
    fun loginIdValidation(
        @Body loginValidationRequest: IdValidationRequest
    ): Call<CommonResponse>

    /**
     * sendAuthenticationCode: 이메일 확인 코드를 위한 api
     * */
    @GET("api/member/signup/code")
    fun sendAuthenticationCode(
        @Query("email", encoded = true) email: String
    ): Call<CommonResponse>

    /**
     * emailCertification: 이메일 확인을 위한 api
     * */
    @POST("api/member/signup/valid-code")
    fun emailCertification(
        @Body emailCertificationRequest: EmailCertificationRequest
    ): Call<CommonResponse>

    /* 희망 직무와 학과 리스트 불러오기 */
    @GET("api/member/job-major")
    fun getJobAndMajorList(): Call<CommonResponse>

    /* 희망 멘토링 분야 리스트 불러오기 */
    @GET("api/fields")
    fun getFieldList(): Call<CommonResponse>

    /* 학과 리스트 불러오기 */
    @GET("api/majors")
    fun getMajorList(): Call<CommonResponse>

    @Multipart
    @POST("api/signup-mento")
    fun signUpAsMentor(
        @Part("password") password: RequestBody,
        @Part("majors") majors: RequestBody,
        @Part("loginId") loginId: RequestBody,
        @Part("school") school: RequestBody,
        @Part("grade") grade: Int,
        @Part("nickname") nickname: RequestBody,
        @Part certifications: List<MultipartBody.Part>,
        @Part("fields") fields: RequestBody,
        @Part("email") email: RequestBody,
        @Part("introduction") introduction: RequestBody,
    ):Call<CommonResponse>

    @Multipart
    @POST("api/signup-menti")
    fun signUpAsMentee(
        @Part("password") password: RequestBody,
        @Part("majors") majors: RequestBody,
        @Part("loginId") loginId: RequestBody,
        @Part("school") school: RequestBody,
        @Part("grade") grade: Int,
        @Part("nickname") nickname: RequestBody,
        @Part certifications: List<MultipartBody.Part>,
        @Part("fields") fields: RequestBody,
        @Part("email") email: RequestBody,
        @Part("introduction") introduction: RequestBody,
    ):Call<CommonResponse>
/*
    *//**
     * searchMentee: 홈에서 menti를 받아오는 api
     * *//*
    @GET("api/menti/expenses")
    fun searchMentee(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<CommonResponse>*/

    /**
     * 홈화면에서 멘토를 받아오는 API
     */
    @GET("api/mento")
    fun getMentor(): Call<CommonResponse>

    /**
     * 홈화면에서 멘티를 받아오는 API
     */
    @GET("api/menti")
    fun getMentee(): Call<CommonResponse>

    @GET("api/menti")
    fun searchMenteeWithMajors(
        @Query("majors") majors: String
    ): Call<CommonResponse>

    @GET("api/menti")
    fun searchMenteeWithJobs(
        @Query("jobs") jobs: String
    ): Call<CommonResponse>

    @GET("api/menti")
    fun searchMenteeWithAllFilter(
        @Query("majors") majors: String,
        @Query("jobs") jobs: String
    ): Call<CommonResponse>

    @GET("api/menti/{id}")
    fun loadMenteeDetailedInfo(
        @Path("id") id: Int
    ): Call<CommonResponse>

    @GET("api/mento/{id}")
    fun loadMentorDetailedInfo(
        @Path("id") id: Int
    ): Call<CommonResponse>

    /**
     * doLogin: 로그인을 진행하는 api
     * */
    @POST("member/login")
    fun doLogin(
        @Body loginRequest: LoginRequest
    ): Call<CommonResponse>

    @POST("api/auth/reissue")
    fun reissue(
    ): Call<CommonResponse>

    /**
     * inSendMessage: 쪽지함에서 쪽지를 보내는 api
     * */
    @POST("api/mento/letter/in/1")
    fun inSendMessage(
        @Body MessageRequest: MessageRequest
    ): Call<CommonResponse>

    @POST("api/mento/letter/out/{mentiid}")
    fun outSendMessage(
        @Body MessageRequest: MessageRequest
    ): Call<CommonResponse>

    /**
     * loadMessageBox: 쪽지함 리스트를 받아오는 api
     * */
    @GET("api/mento/room")
    fun loadMessageBox(
    ): Call<CommonResponse>

    /**
     * loadDetailedMessage: 쪽지함 상세 리스트를 받아오는 api
     * */
    @GET("api/mento/letter/{roomPk}")
    fun loadDetailedMessage(
        @Path("roomPk") roomPk: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<CommonResponse>

    /**
     * getCode: 이메일로 코드를 보내는 api
     * */
    @POST("api/member/code")
    fun getCode(
        @Body EmailCodeRequest: EmailCodeRequest
    ): Call<CommonResponse>

    /**
     * findId: 아이디를 받아오는 api
     * */
    @POST("api/member/loginId")
    fun findId(
        @Body FindIdRequest: FindIdRequest
    ): Call<CommonResponse>


    @POST("api/member/password")
    fun findPwd(
        @Body FindPwdRequest: FindPwdRequest
    ): Call<CommonResponse>




}




object DotoringAPI {
    val retrofitService: DotoringAPIService by lazy {
        retrofit.create(DotoringAPIService::class.java)
    }
    fun getErrorResponse(errorBody: ResponseBody): CommonResponse? {
        return retrofit.responseBodyConverter<CommonResponse>(
            CommonResponse::class.java,
            CommonResponse::class.java.annotations
        ).convert(errorBody)
    }
}

object DotoringRegisterAPI {
    val retrofitService: DotoringAPIService by lazy {
        registerRetrofit.create(DotoringAPIService::class.java)
    }
}

