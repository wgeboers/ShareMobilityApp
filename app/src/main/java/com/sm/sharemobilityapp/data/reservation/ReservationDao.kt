package com.sm.sharemobilityapp.data.reservation

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(reservations: List<Reservation>)

    @Query("SELECT * FROM reservation")
    fun getAll(): Flow<List<Reservation>>

    @Query("SELECT * FROM reservation WHERE userId = :userId")
    fun getReservationsByUserId(userId: Int): Flow<List<Reservation>>

    @Query("DELETE FROM reservation")
    fun deleteAllReservations()
}