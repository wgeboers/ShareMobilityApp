package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image (
    @PrimaryKey
    val id: Int,
    val carID: Long,
    val pathImage: String
    )