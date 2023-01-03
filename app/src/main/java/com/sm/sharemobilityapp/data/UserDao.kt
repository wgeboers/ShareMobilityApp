package com.sm.sharemobilityapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.sm.sharemobilityapp.data.User

@Dao
interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
//    suspend fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(item: User)

//    @Query("SELECT * from user WHERE id = :id")
//    fun getUser(id: Int): Flow<User>
//
//    @Query("SELECT * from user")
//    fun getUsers(): Flow<List<User>>
}