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
import com.example.dotoring_neoul.dto.register.SaveMentoRqDTO
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.net.CookieManager

private const val BASE_URL =
    "http://172.20.10.4:8080/"


//val interceptor = HttpLoggingInterceptor().apply {
//    level = HttpLoggingInterceptor.Level.BODY
//}

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

    @POST("api/member/valid-nickname")
    fun nicknameValidation(
        @Body nicknameValidationRequest: NicknameValidationRequest
    ): Call<CommonResponse>

    @POST("api/member/valid-loginId")
    fun loginIdValidation(
        @Body loginValidationRequest: IdValidationRequest
    ): Call<CommonResponse>

    @GET("api/member/code")
    fun sendAuthenticationCode(
        @Query("email", encoded = true) email: String
    ): Call<CommonResponse>

    @POST("api/member/valid-code")
    fun emailCertification(
        @Body emailCertificationRequest: EmailCertificationRequest
    ): Call<CommonResponse>

    @GET("api/member/job-major")
    fun getJobAndMajorList(): Call<CommonResponse>

    @POST("api/signup-mento")
    fun signUpAsMento(
        @Body mentoSingupRequest: SaveMentoRqDTO
    ): Call<CommonResponse>
    /*
        @Multipart
        @POST("api/signup-mento")
        fun signUpAsMento(
            @Part certifications: List<MultipartBody.Part>,
            @PartMap mentoSignupRequestDTO: MutableMap<String, HashMap<String, RequestBody>>
    //        @Body finalSignUpRequest: FinalSignUpRequest
        ):Call <CommonResponse>*/

    @GET("api/menti")
    fun searchMentee(
    ): Call<CommonResponse>

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

    @GET("api/mento/{id}")
    fun loadMentoDetailedInfo(
        @Path("id") userId: String
    ): Call<CommonResponse>

    @GET("api/menti/{id}")
    fun loadMentiDetailedInfo(
        @Path("id") id: Int
    ): Call<CommonResponse>

    @POST("member/login")
    fun doLogin(
        @Body loginRequest: LoginRequest
    ): Call<CommonResponse>

    @POST("api/auth/reissue")
    fun reissue(
    ): Call<CommonResponse>

    @POST("api/mento/letter/in/1")
    fun inSendMessage(
        @Body MessageRequest: MessageRequest
    ): Call<CommonResponse>

    @POST("api/mento/letter/out/{mentiid}")
    fun outSendMessage(
        @Body MessageRequest: MessageRequest
    ): Call<CommonResponse>

    @GET("api/mento/room")
    fun loadMessageBox(
    ): Call<CommonResponse>

    @GET("api/mento/letter/{roomPk}")
    fun loadDetailedMessage(
        @Path("roomPk") roomPk: Long,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Call<CommonResponse>

    @POST("api/member/code")
    fun getCode(
        @Body EmailCodeRequest: EmailCodeRequest
    ): Call<CommonResponse>
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
}

object DotoringRegisterAPI {
    val retrofitService: DotoringAPIService by lazy {
        registerRetrofit.create(DotoringAPIService::class.java)
    }
}

