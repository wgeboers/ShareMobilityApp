package com.sm.sharemobilityapp

import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.user.User
import com.sm.sharemobilityapp.data.user.UserDao
import junit.framework.Assert.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.internal.wait
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

//@RunWith(AndroidJUnit4::class)
//@SmallTest
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
    fun insertUserTest() = runBlocking {
        val user1 = User (
        -1,
        "CAR_USER",
        "George2",
        "letmein",
        "welkom",
                "Piet",
            "Straat 23 2986CP",
            0)
        userDao.insert(user1)

       // var users: List<User>

       var users = launch { userDao.getUsers().collect() {user ->
           Log.d("User collect", user.toString())
           user.forEach {
               assert("George2" == it.username)
               Log.d("User collect", it.username)
               this.coroutineContext.cancel()
           }
       }

       }



       // Log.d("Test", users.toString())
       // user.collect()
      //  Log.d("User collect", user.toString())



//        val latch = CountDownLatch(1)
//        var result: List<User>
//        val job = async(Dispatchers.IO) {
//           userDao.getUsers().collect() { user ->
//               user.forEach() {
//                   Log.d("forEach", user[0].firstname)
//                   assertEquals("Timo", user[0].firstname)
//               }
//
//            }
//        }
//
//        latch.await()
//        job.cancelAndJoin()
    }
}