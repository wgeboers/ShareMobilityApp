package com.sm.sharemobilityapp.data.image

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class Image(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val carID: Int?,
    val pathImage: String
)