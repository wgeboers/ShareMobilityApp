package com.sm.sharemobilityapp.repository

import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.data.car.asDomainModel
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.ShareMobilityApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CarRepository(private val database: SMRoomDatabase) {
    var cars: Flow<List<CarModel>> = database.carDao.getAll().map { it.asDomainModel() }

    suspend fun refreshCars() {
        withContext(Dispatchers.IO) {
            val carList: List<CarInfo> = ShareMobilityApi.retrofitService.getCars()
            val cars = prepareCars(carList)
            database.carDao.deleteAllCars()
            database.carDao.insertAll(cars)
        }
    }

    // map CarInfo to database Car
    private fun prepareCars(carInfo: List<CarInfo>): List<Car> {
        var carUsers: List<Car> = carInfo.map{
            Car (
                type = it.type,
                id = it.id,
                licensePlate = it.licensePlate,
                make = it.make,
                model = it.model,
                mileage = it.mileage,
                hourlyRate = it.hourlyRate,
                longitude = it.longitude,
                latitude = it.latitude,
                termsOfPickup = it.termsOfPickup,
                termsOfReturn = it.termsOfReturn,
                purchasePrice = it.purchasePrice,
                amountOfYearsOwned = it.amountOfYearsOwned,
                usageCostsPerKm = it.usageCostsPerKm,
                totalCostOfOwnership = it.totalCostOfOwnership,
                rechargeTimeInMinutes = it.rechargeTimeInMinutes,
                maxRangeInKilometers = it.maxRangeInKilometers,
                maxCapacityOfBattery = it.maxCapacityOfBattery
            )
        }

        return carUsers
    }
}