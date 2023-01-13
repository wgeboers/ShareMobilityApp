package com.sm.sharemobilityapp.data

import android.content.Context
import androidx.room.*
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.data.car.CarDao
import com.sm.sharemobilityapp.data.reservation.Reservation
import com.sm.sharemobilityapp.data.reservation.ReservationDao
import com.sm.sharemobilityapp.data.user.User
import com.sm.sharemobilityapp.data.user.UserDao

@Database(entities = [User::class, Car::class, Reservation::class, Image::class], version = 11, exportSchema = false)
abstract class SMRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao
    abstract fun reservationDao(): ReservationDao
    abstract fun imageDao(): ImageDao

fun getDatabase(context: Context): SMRoomDatabase {
    synchronized(SMRoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SMRoomDatabase::class.java,
                "sm_database"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}