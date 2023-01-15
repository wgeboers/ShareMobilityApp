package com.sm.sharemobilityapp.data.car

import androidx.room.*
import com.sm.sharemobilityapp.data.reservation.Reservation
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cars: List<Car>)

    //Get all cars
    @Query("SELECT * FROM car")
    fun getAll(): Flow<List<Car>>

    //Get one car by ic
    @Query("SELECT * FROM car WHERE id = :id")
    fun getCarById(id: Int): Flow<Car>

    //Delete all cars
    @Query("DELETE FROM car")
    fun deleteAllCars()

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)

    @Query("SELECT * from car WHERE id = :id")
    fun getCar(id: Long): Flow<Car>

    @Query("SELECT * FROM car WHERE carOwnerID = :userId")
    fun getcarsByUserId(userId: Int): Flow<List<Car>>

    @Query("SELECT * from car where model LIKE :model")
    fun getCarsByModel(model: String): Flow<List<Car>>

    @Query("SELECT * from car where make LIKE :make")
    fun getCarsByMake(make: String): Flow<List<Car>>

    @Query("SELECT * from car where make LIKE :make and model LIKE :model")
    fun getCarsByModelAndMake(model: String, make: String): Flow<List<Car>>

    @Query("SELECT DISTINCT model FROM car ORDER BY model")
    fun getDistinctModels(): Flow<List<String>>

    @Query("SELECT DISTINCT make FROM car ORDER BY model")
    fun getDistinctMakes(): Flow<List<String>>

    @Query("SELECT * FROM car WHERE make LIKE :make AND model LIKE :model AND (usageCostsPerKm BETWEEN :from AND :to)")
    fun getCarsByFilter(
        make: String,
        model: String,
        from: Double = 0.0,
        to: Double = 999999.0
    ): Flow<List<Car>>

}