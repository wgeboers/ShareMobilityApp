package com.sm.sharemobilityapp.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarInfo(
    val type: String? = null,
    val id: Int? = null,
    val licensePlate: String? = null,
    val carOwner: UserInfo? = null,
    val make: String? = null,
    val model: String? = null,
    val mileage: Int? = null,
    val hourlyRate: Double? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val termsOfPickup: String? = null,
    val termsOfReturn: String? = null,
    val purchasePrice: Int? = null,
    val amountOfYearsOwned: Int? = null,
    val usageCostsPerKm: Double? = null,
    val totalCostOfOwnership: Double? = null,
    val rechargeTimeInMinutes: Double? = null,
    val maxRangeInKilometers: Double? = null,
    val maxCapacityOfBattery: Double? = null,
    val carImages: List<ImageInfo>,
    val fuelType: String? = null,
    val efficiency: Double? = null
)
