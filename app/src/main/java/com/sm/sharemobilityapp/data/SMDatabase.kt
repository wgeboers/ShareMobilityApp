package com.sm.sharemobilityapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sm.sharemobilityapp.data.User

@Database(entities = [User::class, Car::class, Reservation::class, Image::class], version = 8, exportSchema = false)
abstract class SMRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao
    abstract fun reservationDao(): ReservationDao
    abstract fun imageDao(): ImageDao

    companion object {
        private var INSTANCE: SMRoomDatabase? = null

        fun getDatabase(context: Context): SMRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SMRoomDatabase::class.java,
                    "sm_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}