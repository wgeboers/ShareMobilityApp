package com.sm.sharemobilityapp.model

import android.media.Image
import androidx.room.PrimaryKey

data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val image: Int,
    val make: String,
    val model: String,
    val pricePerDay: Double,
    val totalPrice: Double,
    val rentedStartDate: String,
    val rentedEndDate: String
    )