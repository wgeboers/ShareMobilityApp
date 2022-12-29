package com.sm.sharemobilityapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
  suspend fun insert(image: Image)

  @Update
  suspend fun update(image: Image)

  @Delete
  suspend fun delete(image: Image)

  @Query("SELECT * from image WHERE id = :id")
  fun getImage(id: Int): Flow<Image>

  @Query("SELECT * from image")
  fun getImages(): Flow<List<Image>>
}