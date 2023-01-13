package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class RegistrationDto (
    @Json(name="carId")
    val carId: Int,

    @Json(name="carOwnerId")
    val carOwnerId: Int
)