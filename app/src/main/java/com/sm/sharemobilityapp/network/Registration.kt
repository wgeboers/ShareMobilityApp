package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class Registration (
    @Json(name="carId")
    val carId: Int? = null,

    @Json(name="carOwnerId")
    val carOwnerId: Int? = null
)