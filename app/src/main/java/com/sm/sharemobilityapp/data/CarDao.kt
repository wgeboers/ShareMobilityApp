package com.sm.sharemobilityapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insert(car: Car)

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insertAll(car: List<Car>)

    @Update
    suspend fun update(car: Car)

    @Delete
    suspend fun delete(car: Car)
    
    @Query("SELECT * from car WHERE id = :id")
    fun getCar(id: Long): Flow<Car>

    @Query("SELECT * from car")
    fun getCars(): Flow<List<Car>>

    @Query("SELECT * from car where modelval LIKE :model")
    fun getCarsByModel(model: String): Flow<List<Car>>

    @Query("SELECT * from car where makeval LIKE :make")
    fun getCarsByMake(make: String): Flow<List<Car>>

    @Query("SELECT * from car where makeval LIKE :make and modelval LIKE :model")
    fun getCarsByModelAndMake(model: String, make: String): Flow<List<Car>>

    @Query("SELECT DISTINCT modelval FROM car ORDER BY modelval")
    fun getDistinctModels(): Flow<List<String>>

    @Query("SELECT DISTINCT makeval FROM car ORDER BY modelval")
    fun getDistinctMakes(): Flow<List<String>>

    @Query("SELECT * FROM car WHERE makeval LIKE :make AND modelval LIKE :model AND (usageCostsPerKm BETWEEN :from AND :to)")
    fun getCarsByFilter(make: String, model: String, from: Double = 0.0, to: Double = 999999.0) : Flow<List<Car>>

    @Query("DELETE FROM car")
    fun deleteAllCars()
}