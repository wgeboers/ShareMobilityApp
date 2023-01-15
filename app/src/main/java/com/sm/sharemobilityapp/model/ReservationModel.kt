package com.sm.sharemobilityapp.model

data class ReservationModel(
    val id: Int?,
    val carId: Int,
    val userId: Int,
    val startReservation: String,
    val endReservation: String,
)