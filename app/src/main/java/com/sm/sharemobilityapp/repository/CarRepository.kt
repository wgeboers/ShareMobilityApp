package com.sm.sharemobilityapp.repository

import android.util.Log
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.data.car.asDomainModel
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.RegistrationInfo
import com.sm.sharemobilityapp.network.RegistrationDto
import com.sm.sharemobilityapp.network.ShareMobilityApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonArray
import retrofit2.Response

class CarRepository(private val database: SMRoomDatabase) {
    var cars: Flow<List<CarModel>> = database.carDao.getAll().map { it.asDomainModel() }
    var brands: Flow<List<String>> = database.carDao.getBrands()
    var models: Flow<List<String>> = database.carDao.getModels()

    suspend fun refreshCars() {
        withContext(Dispatchers.IO) {
            val carList: Response<List<CarInfo>> = ShareMobilityApi.retrofitService.getCars()
            val cars = prepareCars(carList)
            database.carDao.deleteAllCars()
            if (cars != null) {
                database.carDao.insertAll(cars)
            }
        }
    }

    suspend fun insertCar(carInfo: CarInfo) : Response<CarInfo> {
        var returnInfo: Response<CarInfo>
        withContext(Dispatchers.IO) {
            returnInfo = ShareMobilityApi.retrofitService.postCar(carInfo)
        }
        return returnInfo
    }

    suspend fun getCarsByUser(userId: Int): Flow<List<CarModel>> {
        val cars: Flow<List<CarModel>> =
            database.carDao.getcarsByUserId(userId).map { it.asDomainModel() }

        return cars
    }

    suspend fun postRegistration(carId: Int, ownerId: Int) : Response<RegistrationInfo> {
        Log.d("DATAREP", carId.toString() + ' ' + ownerId.toString())
        return ShareMobilityApi.retrofitService.postRegistration(
            RegistrationDto(
            carId = carId,
            carOwnerId = ownerId
            )
        )
    }

    // map CarInfo to database Car
    private fun prepareCars(carInfo: Response<List<CarInfo>>): List<Car>?{
        var carUsers: List<Car>? = carInfo.body()?.map {
            Car(
                type = it.type,
                id = it.id,
                licensePlate = it.licensePlate,
                carOwnerID = it.carOwner?.id?.toInt() ?: 0,
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
                maxCapacityOfBattery = it.maxCapacityOfBattery,
                fuelType = it.fuelType
            )
        }

        return carUsers
    }
}