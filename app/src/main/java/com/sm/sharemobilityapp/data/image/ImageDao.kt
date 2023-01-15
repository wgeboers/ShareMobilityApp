package com.sm.sharemobilityapp.data.image

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insert(image: Image)

    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insertAll(image: List<Image>)

    @Update
    suspend fun update(image: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("DELETE FROM image")
    suspend fun deleteAll()

    @Query("SELECT * from image WHERE id = :id")
    fun getImage(id: Int): Flow<Image>

    @Query("SELECT * from image")
    fun getImages(): Flow<List<Image>>
}