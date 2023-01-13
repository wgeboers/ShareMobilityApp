package com.sm.sharemobilityapp.repository

import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.data.user.User
import com.sm.sharemobilityapp.data.user.asDomainModel
import com.sm.sharemobilityapp.model.UserModel
import com.sm.sharemobilityapp.network.ShareMobilityApi
import com.sm.sharemobilityapp.network.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UserRepository(private val database: SMRoomDatabase) {
    var users: Flow<List<UserModel>> = database.userDao.getAll().map { it.asDomainModel() }

    suspend fun refreshUsers() {
        withContext(Dispatchers.IO) {
            val userList: List<UserInfo> = ShareMobilityApi.retrofitService.getUsers()
            val users = prepareUsers(userList)
            database.userDao.deleteAllUsers()
            database.userDao.insertAll(users)
        }
    }

    private fun prepareUsers(userInfo: List<UserInfo>): List<User> {
        var userMapping: List<User> = userInfo.map {
            User (
                id = it.id,
                type = it.type,
                username = it.username,
                password = it.password,
                firstname = it.firstname,
                lastname = it.lastname,
                address = it.address
            )
        }

        return userMapping
    }
}