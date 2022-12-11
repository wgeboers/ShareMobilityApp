package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class Reservation(

	@Json(name="endReservation")
	val endReservation: String? = null,

	@Json(name="startReservation")
	val startReservation: String? = null,

	@Json(name="userId")
	val userId: Int? = null,

	@Json(name="carId")
	val carId: Int? = null
)
