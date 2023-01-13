package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Timestamp

@Entity(tableName = "reservation")
data class Reservation (
    @PrimaryKey
    val id: Long,
    val carId: Int,
    val userId: Int,
    val startReservation: String,
    val endReservation: String
)