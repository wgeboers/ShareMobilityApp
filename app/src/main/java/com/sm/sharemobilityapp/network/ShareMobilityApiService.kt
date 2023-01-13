package com.sm.sharemobilityapp.network

import com.sm.sharemobilityapp.data.reservation.Reservation
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private const val BASE_URL = "http://192.168.1.205:8080/"
private val logging : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val apiInterceptor : OkHttpClient = OkHttpClient.Builder().apply {
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
    suspend fun getCars(): List<CarInfo>

    //Reservation
    @GET("reservation")
    suspend fun getReservations(): List<ReservationInfo>

    @POST("reservation")
    suspend fun postReservation(@Body reservation: Reservation): ReservationInfo

    //User
    @GET("users")
    suspend fun getUsers(): List<UserInfo>
}

object ShareMobilityApi {
    val retrofitService : ShareMobilityApiService by lazy {
        retrofit.create(ShareMobilityApiService::class.java)
    }
}