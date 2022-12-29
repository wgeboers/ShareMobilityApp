package com.sm.sharemobilityapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // Ignore if primary key already exists
    suspend fun insert(reservation: Reservation)

    @Update
    suspend fun update(reservation: Reservation)

    @Delete
    suspend fun delete(reservation: Reservation)

    @Query("SELECT * from reservation WHERE id = :id")
    fun getReservation(id: Int): Flow<Reservation>

    @Query("SELECT * from reservation")
    fun getReservations(): Flow<List<Reservation>>
}