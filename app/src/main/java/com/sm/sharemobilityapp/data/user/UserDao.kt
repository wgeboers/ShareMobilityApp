package com.sm.sharemobilityapp.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("DELETE FROM user")
    fun deleteAllUsers()
}