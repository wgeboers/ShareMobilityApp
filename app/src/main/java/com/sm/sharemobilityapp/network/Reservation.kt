package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class Reservation(

	@Json(name="id")
	val id: Long,

	@Json(name="car")
	val carInfo: CarInfo,

	@Json(name="user")
	val userInfo: UserInfo,

	@Json(name="endReservation")
	val endReservation: String? = null,

	@Json(name="startReservation")
	val startReservation: String? = null,
)
