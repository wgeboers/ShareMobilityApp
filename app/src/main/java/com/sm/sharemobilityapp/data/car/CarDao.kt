package com.sm.sharemobilityapp.data.car

import androidx.room.*
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
}