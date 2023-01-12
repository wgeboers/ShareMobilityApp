package com.sm.sharemobilityapp.repository

import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import com.sm.sharemobilityapp.data.*
import com.sm.sharemobilityapp.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response

class DataRepository (private val database: SMRoomDatabase){
    val cars: Flow<List<Car>>
        get() = database.carDao().getCars()
    val users: Flow<List<User>>
        get() = database.userDao().getUsers()
    val reservations: Flow<List<Reservation>>
        get() = database.reservationDao().getReservations()
    val models: Flow<List<String>>
        get() = database.carDao().getDistinctModels()
    val makes: Flow<List<String>>
        get() = database.carDao().getDistinctMakes()

    //--> Start data User
    suspend fun getLogin(username: String, password: String): UserInfo {
        var userInfo = UserInfo();
        withContext(Dispatchers.IO) {
            userInfo = ShareMobilityApi.retrofitService.getLogin(username, password)
        }
        return userInfo
    }

    suspend fun getAllUsers(refesh: Boolean): Flow<List<User>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                val usersInfo: List<UserInfo>? = ShareMobilityApi.retrofitService.getUsers()
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

    suspend fun updateUser(userInfo: UserInfo, userId: Long) {
        withContext(Dispatchers.IO) {
            ShareMobilityApi.retrofitService.putUser(userInfo, userId)
        }
    }

    suspend fun deleteUser(id: Long) {
        withContext(Dispatchers.IO) {
            ShareMobilityApi.retrofitService.deleteUser(id)
        }
    }

    // map UserInfo to database user
    private fun prepareUsers(userInfo: List<UserInfo>?): List<User> {
        var listUsers: List<User> = userInfo?.map {
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
        } ?: listOf<User>()
        return listUsers
    }
    //--> End data Users

    //--> Start data Car
    suspend fun getAllCars() {
        withContext(Dispatchers.IO) {
            database.carDao().deleteAllCars()
            val carList: List<CarInfo> = ShareMobilityApi.retrofitService.getCars()
            val imageList: MutableList<Image> = mutableListOf()
            val ownerList: MutableList<User> = mutableListOf()

            // CarInfo can contain an Owner and images, extract the owner and images
            for (car in carList) {
                val imageInfoList = car.carImages
                val id = car
                imageInfoList.map {
                    imageList.add (
                        Image (
                            carID = car.id,
                            pathImage = it.imagePath
                                )
                            )
                }
                if (car.carOwner?.id != null) {
                    ownerList.add(
                        User(
                            id = car.carOwner?.id,
                            type = car.carOwner?.type,
                            username = car.carOwner?.username,
                            password = car.carOwner?.password,
                            firstname = car.carOwner?.password,
                            lastname = car.carOwner?.lastname,
                            address = car.carOwner?.address,
                            bonusPoints = car.carOwner?.bonuspoints ?: 0
                        )
                    )
                }
            }
            database.imageDao().deleteAll()
            database.imageDao().insertAll(imageList)
            database.userDao().deleteAll()
            database.userDao().insertAll(ownerList)
            val cars = prepareCars(carList)
            database.carDao().insertAll(cars)
        }
    }

    suspend fun insertCars(cars: List<Car>) {
        withContext(Dispatchers.IO) {
            database.carDao().insertAll(cars)
        }
    }

    suspend fun getCarsByModel(model: String, refesh: Boolean): Flow<List<Car>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                getAllCars()
            }
        }
        return database.carDao().getCarsByModel(model)
    }

    suspend fun getCarsByMake(make: String, refesh: Boolean): Flow<List<Car>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                getAllCars()
            }
        }
        return database.carDao().getCarsByMake(make)
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

    suspend fun insertCar(carInfo: CarInfo) : Response<CarInfo> {
        var returnInfo: Response<CarInfo>
        Log.d("ADDCAR carINfo", carInfo.toString())
        withContext(Dispatchers.IO) {
            returnInfo = ShareMobilityApi.retrofitService.postCar(carInfo)
        }
        Log.d("DATAREP", returnInfo.toString())
        return returnInfo
    }

    suspend fun deleteCar(id: Long) {
        withContext(Dispatchers.IO) {
            ShareMobilityApi.retrofitService.deleteCar(id)
        }
    }

    fun getCarsByFilter(make: String, model: String, from: Double, to: Double) : Flow<List<Car>> {
        return database.carDao().getCarsByFilter(make, model, from, to)
    }

    // map CarInfo to database Car
    private fun prepareCars(carInfo: List<CarInfo>): List<Car> {
        var carUsers: List<Car> = carInfo.map{
            Car (
                id = it.id,
                licensePlate = it.licensePlate,
                carOwnerID = it.carOwner?.id ?: 0,
                makeval = it.make,
                modelval = it.model,
                mileageval = it.mileage,
                hourlyRate = it.hourlyRate,
                longitude = it.longitude,
                latitude = it.latitude,
                termsOfPickup = it.termsOfPickup,
                termsOfReturn = it.termsOfReturn,
                purchasePriceval = it.purchasePrice,
                amountOfYearsOwned = it.amountOfYearsOwned,
                usageCostsPerKm = it.usageCostsPerKm,
                totalCostOfOwnership = it.totalCostOfOwnership
            )
        }

        return carUsers
    }
    //--> End data Car

    //--> Start data Reservation
    suspend fun getAllReservations(refesh: Boolean): Flow<List<Reservation>> {
        if (refesh) {
            withContext(Dispatchers.IO) {
                database.reservationDao().deleteAll()
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

    suspend fun postRegistration(carId: Int, ownerId: Int) : Response<Registration>{
        Log.d("DATAREP", carId.toString() + ' ' + ownerId.toString())
        return ShareMobilityApi.retrofitService.postRegistration(RegistrationDto(
            carId = carId,
            carOwnerId = ownerId
        ))
    }

   // suspend fun insertRegistration(re)

    //--> End data registration
}