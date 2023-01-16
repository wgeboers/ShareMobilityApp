package com.sm.sharemobilityapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.user.User
import com.sm.sharemobilityapp.data.user.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class UserDaoTest {
    private lateinit var database: SMRoomDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SMRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        userDao = database.userDao
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertUser() = runBlocking {
        val user1 = User (
        -1,
        "CAR_USER",
        "George",
        "letmein",
        "welkom" +
                "Piet",
            "Goeber",
            "Straat 23 2986 CP",
            0)
        userDao.insert(user1)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            userDao.getUsers().collect() {
                assert(it[0].firstname.contains("welkom"))
                latch.countDown()
            }
        }
        latch.wait()
        job.cancelAndJoin()
    }
}