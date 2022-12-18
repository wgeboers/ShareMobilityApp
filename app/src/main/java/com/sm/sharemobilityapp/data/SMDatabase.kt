package com.sm.sharemobilityapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sm.sharemobilityapp.data.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SMRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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