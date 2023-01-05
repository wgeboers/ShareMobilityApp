package com.sm.sharemobilityapp.repository

import android.util.Log
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.data.Reservation
import com.sm.sharemobilityapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DataRepository (private val database: SMRoomDatabase){
    val cars: Flow<List<Car>> = database.carDao().getCars()
    val users: Flow<List<User>> = database.userDao().getUsers()
    val reservations: Flow<List<Reservation>> = database.reservationDao().getReservations()


    //--> Start data User
    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val usersInfo = ShareMobilityApi.retrofitService.getUsers()
            Log.d("Datarep", usersInfo.toString())
            val users = prepareUsers(usersInfo)
            Log.d("users", users.toString().toString())
            database.userDao().insertAll(users)
        }
    }

    suspend fun getAllUsers(refesh: Boolean): Flow<List<User>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val usersInfo = ShareMobilityApi.retrofitService.getUsers()
                val users = prepareUsers(usersInfo)
                database.userDao().deleteAll()
                database.userDao().insertAll(users)
            }
        }
        return database.userDao().getUsers()
    }

    suspend fun insertUser(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {
            ShareMobilityApi.retrofitService.postUser(userInfo)
            database.userDao().insertAll(prepareUsers(listOf(userInfo)))
        }
    }

    // map UserInfo to database user
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

    //--> End data Users

    //--> Start data Car
    suspend fun getCarsByModel(model: String, refesh: Boolean): Flow<List<Car>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val carList = ShareMobilityApi.retrofitService.getCars()
                Log.d("CarList", carList.toString())
                val cars = prepareCars(carList)
                Log.d("Cars converted", cars.toString().toString())
                database.carDao().deleteAllCars()
                database.carDao().insertAll(cars)
            }
        }
        return database.carDao().getCars()
    }

    suspend fun getCarsByMake(make: String, refesh: Boolean): Flow<List<Car>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val carList = ShareMobilityApi.retrofitService.getCarbyMake(make)
                val cars = prepareCars(carList)
                database.carDao().deleteAllCars()
                database.carDao().insertAll(cars)
            }
        }
        return database.carDao().getCars()
    }

    suspend fun getCarsByModelAndMake(model: String, make: String, refesh: Boolean): Flow<List<Car>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val carByMake = ShareMobilityApi.retrofitService.getCarbyMake(make)
                database.carDao().insertAll(prepareCars(carByMake))
                val carByModel = ShareMobilityApi.retrofitService.getCarbyModel(model)
                database.carDao().insertAll(prepareCars(carByModel))
            }
        }
        return database.carDao().getCarsByModelAndMake(model, make)
    }

    suspend fun getAllCars() {
        withContext(Dispatchers.IO) {
            database.carDao().deleteAllCars()
            val carList = ShareMobilityApi.retrofitService.getCars()
            val cars = prepareCars(carList)
            database.carDao().insertAll(cars)
        }
    }

    suspend fun insertCars(cars: List<Car>) {
        withContext(Dispatchers.IO) {
            database.carDao().insertAll(cars)
        }
    }

    // map CarInfo to database Car
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

    //--> End data Car

    //--> Start data Reservation

    suspend fun getAllReservations(refesh: Boolean): Flow<List<Reservation>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val reservationInfo = ShareMobilityApi.retrofitService.getAllReservations()
                database.reservationDao().insertAll(prepareReservation(reservationInfo))
            }
        }
        return database.reservationDao().getReservations()
    }

    suspend fun insertReservation(reservationInfo: ReservationInfo) {
        ShareMobilityApi.retrofitService.postReservation(reservationInfo)
        database.reservationDao().insertAll(prepareReservation(listOf(reservationInfo)))
    }

    private fun prepareReservation(reservation: List<ReservationInfo>): List<Reservation> {
        var reservations: List<Reservation> = reservation.map{
            Reservation(
                id = it.id,
                carId = it.carInfo.id ?: 0,
                userId = it.userInfo.id ?: 0,
                startReservation = it.startReservation ?: "00:00:00 00-00-00",
                endReservation = it.endReservation ?: "00:00:00 00-00-00"
            )
        }
        return reservations
    }

    //--> End data reservation

    //--> Start data registration

    suspend fun getRegistrationByOwnerId(id: Long): List<Registration> {
        return ShareMobilityApi.retrofitService.getAllRegistrationsById(id)
    }

   // suspend fun insertRegistration(re)

    //--> End data registration
}