package com.sm.sharemobilityapp.repository

import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.asDataBaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val database: SMRoomDatabase) {
    suspend fun refreshUser() {
        withContext(Dispatchers.IO) {
            val user = ShareMobilityApi.retrofitService.getUsers()
            //database.userDao.insertAll(user.asDataBaseModel())
        }
    }
}