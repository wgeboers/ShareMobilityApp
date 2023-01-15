package com.sm.sharemobilityapp.data.user

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(item: User)

    @Query("SELECT * from user WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}