package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class RegistrationInfo(
    @Json(name = "type")
    val type: String? = null,

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "licensePlate")
    val licensePlate: String? = null,

    @Json(name = "carOwner")
    val carOwner: UserInfo? = null,

    @Json(name = "make")
    val make: String? = null,

    @Json(name = "model")
    val model: String? = null,

    @Json(name = "mileage")
    val mileage: Int? = null,

    @Json(name = "hourlyRate")
    val hourlyRate: Double? = null,

    @Json(name = "longitude")
    val longitude: Double? = null,

    @Json(name = "latitude")
    val latitude: Double? = null,

    @Json(name = "termsOfPickup")
    val termsOfPickup: String? = null,

    @Json(name = "termsOfReturn")
    val termsOfReturn: String? = null,

    @Json(name = "purchasePrice")
    val purchasePrice: Int? = null,

    @Json(name = "amountOfYearsOwned")
    val amountOfYearsOwned: Int? = null,

    @Json(name = "usageCostsPerKm")
    val usageCostsPerKm: Double? = null,

    @Json(name = "totalCostOfOwnership")
    val totalCostOfOwnership: Double? = null,

    @Json(name = "carImages")
    val carImages: List<Any?>? = null,

    @Json(name = "fuelType")
    val fuelType: String? = null,

    @Json(name = "efficiency")
    val efficiency: Double? = null
)