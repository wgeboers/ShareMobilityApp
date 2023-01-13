package com.sm.sharemobilityapp.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class CarModel(
    val id: Int,
    val type: String,
    val licensePlate: String,
    val make: String,
    val model: String,
    val mileage: Int,
    val hourlyRate: Double,
    val latitude: Double,
    val longitude: Double,
    val termsOfPickUp: String,
    val termsOfReturn: String,
    val purchasePrice: Int,
    val amountOfYearsOwned: Int,
    val usageCostsPerKm: Double,
    val totalCostOfOwnership: Double,
    val rechargeTimeInMinutes: Double,
    val maxRangeInKilometers: Double,
    val maxCapacityOfBattery: Double
): ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
    override fun getTitle(): String = make
    override fun getSnippet(): String = model
    }