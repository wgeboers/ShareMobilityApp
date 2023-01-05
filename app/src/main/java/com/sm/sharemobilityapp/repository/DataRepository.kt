package com.sm.sharemobilityapp.repository

import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository (private val database: SMRoomDatabase){
    private lateinit var listUsers: List<User>

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val usersInfo = ShareMobilityApi.retrofitService.getUsers()
            val users = prepareUsers(usersInfo)
            database.userDao().insertAll(users)
        }
    }

    private fun prepareUsers(userInfo: List<UserInfo>): List<User> {
        return listUsers.map {
            User (
                id = it.id,
                type = it.type,
                username = it.username,
                password = it.password,
                firstname = it.password,
                lastname = it.lastname,
                address = it.address,
                bonusPoints = it.bonusPoints
                    )
        }
    }
}