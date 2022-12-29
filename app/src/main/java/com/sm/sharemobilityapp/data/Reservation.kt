package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Timestamp

@Entity(tableName = "reservation")
data class Reservation (
    @PrimaryKey
    val id: Int,
    val carId: Long,
    val userId: Long,
    val startReservation: String,
    val endReservation: String
)