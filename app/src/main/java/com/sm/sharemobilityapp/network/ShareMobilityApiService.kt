package com.sm.sharemobilityapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

private const val BASE_URL ="http://192.168.183.102:8080/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ShareMobilityApiService {

    @GET("users")
    suspend fun getUsers() : List<UserInfo>

    @GET("cars")
    suspend fun getCars() : List<CarInfo>
}

// Pulic object that exposes the ShareMobility service
object ShareMobilityApi {
    val retrofitService: ShareMobilityApiService by lazy {
        retrofit.create(ShareMobilityApiService::class.java)
    }
}