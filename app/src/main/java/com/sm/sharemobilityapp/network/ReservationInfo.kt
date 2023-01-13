package com.sm.sharemobilityapp.network

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReservationInfo(
    val id: Int,

    @Json(name = "car")
    val carInfo: CarInfo,

    @Json(name = "user")
    val userInfo: UserInfo,

    val startReservation: String,
    val endReservation: String,
)
