package com.sm.sharemobilityapp.data.reservation

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sm.sharemobilityapp.model.ReservationModel

@Entity
data class Reservation constructor(
    @PrimaryKey val id: Int?,
    val carId: Int,
    val userId: Int,
    val startReservation: String,
    val endReservation: String,
)

fun List<Reservation>.asDomainModel(): List<ReservationModel> {
    return map {
        ReservationModel(
            id = it.id,
            carId = it.carId,
            userId = it.userId,
            startReservation = it.startReservation,
            endReservation = it.endReservation
        )
    }
}