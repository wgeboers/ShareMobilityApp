package com.sm.sharemobilityapp.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarInfo(
    val type: String,
    val id: Int,
    val licensePlate: String,
    val make: String,
    val model: String,
    val mileage: Int,
    val hourlyRate: Double,
    val longitude: Double,
    val latitude: Double,
    val termsOfPickup: String,
    val termsOfReturn: String,
    val purchasePrice: Int,
    val amountOfYearsOwned: Int,
    val usageCostsPerKm: Double,
    val totalCostOfOwnership: Double,
    val rechargeTimeInMinutes: Double,
    val maxRangeInKilometers: Double,
    val maxCapacityOfBattery: Double
)
