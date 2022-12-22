package com.sm.sharemobilityapp.model

import android.media.Image

data class Car(
    val image: Int,
    val make: String,
    val model: String,
    val pricePerDay: Double,
    val totalPrice: Double
    )