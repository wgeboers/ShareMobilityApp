package com.sm.sharemobilityapp.network

import com.sm.sharemobilityapp.data.reservation.Reservation
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "http://192.168.1.205:8080/"
private val logging: HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val apiInterceptor: OkHttpClient = OkHttpClient.Builder().apply {
    this.addNetworkInterceptor(logging)
}.build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(apiInterceptor)
    .build()

interface ShareMobilityApiService {
    //Cars
    @GET("cars")
    suspend fun getCars(): Response<List<CarInfo>>

    @POST(value = "cars")
    suspend fun postCar(@Body carInfo: CarInfo): Response<CarInfo>

    //Reservation
    @GET("reservation")
    suspend fun getReservations(): Response<List<ReservationInfo>>

    @POST("reservation")
    suspend fun postReservation(@Body reservation: Reservation): ReservationInfo

    @GET("reservation/byUser/{id}")
    suspend fun getReservationByUser(id: Int): List<Reservation>

    //User
    @GET("users")
    suspend fun getUsers(): List<UserInfo>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): UserInfo

    @GET("users/{id}")
    suspend fun getUserWithResponse(@Path("id") userId: Int): Response<UserInfo>

    @GET("users/login")
    suspend fun loginWithResponse(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<UserInfo>

    @POST(value = "users")
    suspend fun postUser(@Body userInfo: UserInfo): UserInfo

    @DELETE("users/{id}")
    suspend fun deleteUserWithResponse(@Path("id") userId: Int): Response<ResponseBody>

    @PUT(value = "users/{id}")
    suspend fun putUser(@Body userInfo: UserInfo, @Path("id") userId: Int): Response<UserInfo>

    // Registration
    @GET("carsByOwner/cars_owned/{id}")
    suspend fun getAllRegistrationsById(@Path("id") id: Int): List<RegistrationInfo>

    @POST(value = "carsByOwner")
    suspend fun postRegistration(@Body registrationDto: RegistrationDto): Response<RegistrationInfo>
}

object ShareMobilityApi {
    val retrofitService: ShareMobilityApiService by lazy {
        retrofit.create(ShareMobilityApiService::class.java)
    }
}