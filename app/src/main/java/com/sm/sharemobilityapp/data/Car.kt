package com.sm.sharemobilityapp.data

import android.media.Image
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car")
data class Car (
    @PrimaryKey
    val id: Long,
    val licensePlate: String,
    val carOwnerID: Int,
    val makeval: String,
    val modelval: String,
    val mileageval: Int,
    val hourlyRate: Double,
    val longitude: Double,
    val latitude: Double,
    val termsOfPickup: String,
    val termsOfReturn: String,
    val purchasePriceval: Int,
    val amountOfYearsOwned: Int,
    val usageCostsPerKm: Double,
    val totalCostOfOwnership: Double,
)