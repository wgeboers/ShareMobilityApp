package com.sm.sharemobilityapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL ="http://192.168.1.134:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ShareMobilityApiService {

    @GET("users")
    suspend fun getUsers(): List<UserInfo>

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Long): UserInfo

    @GET("users/login")
    suspend fun getLogin(@Query("username") username: String, @Query("password") password: String): UserInfo

    @POST(value = "users")
    suspend fun postUser(@Body userInfo: UserInfo): UserInfo

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Long)

    @PUT(value = "users/{id}")
    suspend fun putUser(@Body userInfo: UserInfo, @Path("id") userId: Long): UserInfo

    @GET("cars")
    suspend fun getCars() : List<CarInfo>
}

// Public object that exposes the ShareMobility service
object ShareMobilityApi {
    val retrofitService: ShareMobilityApiService by lazy {
        retrofit.create(ShareMobilityApiService::class.java)
    }
}