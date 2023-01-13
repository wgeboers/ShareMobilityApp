package com.sm.sharemobilityapp.data

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car (
    @PrimaryKey
    val id: Int? = null,
    val licensePlate: String? = null,
    val carOwnerID: Int? = null,
    val makeval: String? = null,
    val modelval: String? = null,
    val mileageval: Int? = null,
    val hourlyRate: Double? = null,
    val longitude: Double? = null,
    val latitude: Double? = null,
    val termsOfPickup: String? = null,
    val termsOfReturn: String? = null,
    val purchasePriceval: Int? = null,
    val amountOfYearsOwned: Int? = null,
    val usageCostsPerKm: Double? = null,
    val totalCostOfOwnership: Double? = null,
)