package com.example.dotoring_neoul.network

import com.example.dotoring_neoul.MyApplication
import com.example.dotoring_neoul.dto.CommonResponse
import com.example.dotoring_neoul.dto.login.LoginRequest
import com.example.dotoring_neoul.dto.message.MessageRequest
import com.example.dotoring_neoul.dto.register.EmailCertificationRequest
import com.example.dotoring_neoul.dto.register.IdValidationRequest
import com.example.dotoring_neoul.dto.register.MentoSignupRequestDTO
import com.example.dotoring_neoul.dto.register.NicknameValidationRequest
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

val gson : Gson = GsonBuilder()
    .setLenient()
    .create()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(client)
    .build()

val registerRetrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .client(registerClient)
    .build()


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
        @Body mentoSingupRequest: MentoSignupRequestDTO
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

