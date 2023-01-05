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

    @Query("SELECT * from car where modelval like :model")
    fun getCarByModel(model: String): Flow<List<Car>>

    @Query("SELECT * from car where makeval like :make")
    fun getCarByMake(make: String): Flow<List<Car>>
}