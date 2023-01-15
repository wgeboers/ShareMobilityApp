package com.sm.sharemobilityapp.data.car


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.network.UserInfo

@Entity
data class Car constructor(
    @PrimaryKey val id: Int?,
    val type: String?,
    val licensePlate: String?,
    val carOwnerID: Int? = null,
    val make: String?,
    val model: String?,
    val mileage: Int?,
    val hourlyRate: Double?,
    val latitude: Double?,
    val longitude: Double?,
    val termsOfPickup: String?,
    val termsOfReturn: String?,
    val purchasePrice: Int?,
    val amountOfYearsOwned: Int?,
    val usageCostsPerKm: Double?,
    val totalCostOfOwnership: Double?,
    val rechargeTimeInMinutes: Double?,
    val maxRangeInKilometers: Double?,
    val maxCapacityOfBattery: Double?,
    val fuelType: String?
)

fun List<Car>.asDomainModel(): List<CarModel> {
    return map {
        CarModel(
            id = it.id,
            type = it.type,
            licensePlate = it.licensePlate,
            make = it.make,
            model = it.model,
            mileage = it.mileage,
            hourlyRate = it.hourlyRate,
            latitude = it.latitude,
            longitude = it.longitude,
            termsOfPickUp = it.termsOfPickup,
            termsOfReturn = it.termsOfReturn,
            purchasePrice = it.purchasePrice,
            amountOfYearsOwned = it.amountOfYearsOwned,
            usageCostsPerKm = it.usageCostsPerKm,
            totalCostOfOwnership = it.totalCostOfOwnership,
            rechargeTimeInMinutes = it.rechargeTimeInMinutes,
            maxRangeInKilometers = it.maxRangeInKilometers,
            maxCapacityOfBattery = it.maxCapacityOfBattery,
            fuelType = it.fuelType
        )
    }
}