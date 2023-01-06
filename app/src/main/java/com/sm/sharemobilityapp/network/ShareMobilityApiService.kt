package com.sm.sharemobilityapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL ="http://10.0.0.2:8080/"

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

    @GET("users/{id}")
    suspend fun getUserWithResponse(@Path("id") userId: Long): Response<UserInfo>

    @GET("users/login")
    suspend fun getLogin(@Query("username") username: String, @Query("password") password: String): UserInfo

    @POST(value = "users")
    suspend fun postUser(@Body userInfo: UserInfo): UserInfo

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") userId: Long)

    @DELETE("users/{id}")
    suspend fun deleteUserWithResponse(@Path("id") userId: Long): Response<ResponseBody>

    @PUT(value = "users/{id}")
    suspend fun putUser(@Body userInfo: UserInfo, @Path("id") userId: Long): UserInfo

    // Cars
    @GET("cars")
    suspend fun getCars() : List<CarInfo>

    @GET("cars/{id}")
    suspend fun getCar(@Path("id") carId: Long): CarInfo

    @GET("cars/{make}")
    suspend fun getCarbyMake(@Path("make") carMake: String): CarInfo

    @GET("cars/{model}")
    suspend fun getCarbyModel(@Path("model") carModel: String): CarInfo

    @DELETE("cars/{id}")
    suspend fun deleteCar(@Path("id") carId: Long)

    @PUT(value = "cars/{id}")
    suspend fun putCar(@Body carInfo: CarInfo, @Path("id") carId: Long): CarInfo

    @POST(value = "cars")
    suspend fun postCar(@Body carInfo: CarInfo)

    // Registration
    @GET("carsByOwner/cars_owned/{id}")
    suspend fun getAllRegistrationsById(@Path("id") id: Long): Registration

    @POST(value = "carsByOwner")
    suspend fun postRegistration(@Body registration: Registration): Registration

    @DELETE("carsByowner")
    suspend fun deleteRegistration(@Body registration: Registration)

    // Reservation
    @GET("reservation")
    suspend fun getAllReservations(): List<Reservation>

    @GET("reservaton/{id}")
    suspend fun getReservation(id: Long): Reservation

    @GET("reservation/byCar/{id")
    suspend fun getReservationByCarId(id: Long): List<Reservation>

    @GET("reservation/byUser")
    suspend fun getReservationByUser(id: Long): List<Reservation>

    @DELETE("reservation/{id}")
    suspend fun deleteReservation(@Path("id") id: Long)

    @PUT(value = "reservation/{id)")
    suspend fun updateReservationbyId(@Path("id") id: Long, @Body reservation: Reservation): Reservation

    @POST(value = "reservation")
    suspend fun postReservation(@Body reservation: Reservation): Reservation

}

// Public object that exposes the ShareMobility service
object ShareMobilityApi {
    val retrofitService: ShareMobilityApiService by lazy {
        retrofit.create(ShareMobilityApiService::class.java)
    }
}