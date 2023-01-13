package com.sm.sharemobilityapp.model

import androidx.room.PrimaryKey

data class ReservationModel(
    val id: Int?,
    val carId: Int,
    val userId: Int,
    val startReservation: String,
    val endReservation: String,
)