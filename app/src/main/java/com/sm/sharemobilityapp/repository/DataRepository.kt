package com.sm.sharemobilityapp.repository

import android.util.Log
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.logging.Logger

class DataRepository (private val database: SMRoomDatabase){
    //private var listUsers: List<User> = emptyList()

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val usersInfo = ShareMobilityApi.retrofitService.getUsers()
            Log.d("Datarep", usersInfo.toString())
            val users = prepareUsers(usersInfo)
            Log.d("users", users.toString().toString())
            database.userDao().insertAll(users)
        }
    }

    private fun prepareUsers(userInfo: List<UserInfo>): List<User> {
        var listUsers: List<User> = userInfo.map {
            User (
                id = it.id,
                type = it.type,
                username = it.username,
                password = it.password,
                firstname = it.password,
                lastname = it.lastname,
                address = it.address,
                bonusPoints = it.bonuspoints
                    )
        }
        return listUsers
    }

    suspend fun getCarsByModel(model: String) {
        withContext(Dispatchers.IO) {
            val carList = ShareMobilityApi.retrofitService.getCars()
            Log.d("CarList", carList.toString())
            val cars = prepareCars(carList)
            Log.d("users", cars.toString().toString())
            database.carDao().insertAll(cars)
        }
    }

    private fun prepareCars(carInfo: List<CarInfo>): List<Car> {
        var carUsers: List<Car> = carInfo.map {
            Car (
                id = it.id,
                licensePlate = it.licensePlate,
                carOwnerID = it.carOwner?.id ?: 0,
                makeval = it.make,
                modelval = it.model,
                mileageval = it.mileage,
                hourlyRate = it.hourlyRate.toString().toDouble(),
                longitude = it.longitude.toString().toDouble(),
                latitude = it.latitude.toString().toDouble(),
                termsOfPickup = it.termsOfPickup,
                termsOfReturn = it.termsOfReturn,
                purchasePriceval = it.purchasePrice,
                amountOfYearsOwned = it.amountOfYearsOwned,
                usageCostsPerKm = it.usageCostsPerKm.toString().toDouble(),
                totalCostOfOwnership = it.totalCostOfOwnership.toString().toDouble()
            )
        }
        return carUsers
    }
}