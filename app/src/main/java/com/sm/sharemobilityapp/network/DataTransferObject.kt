package com.sm.sharemobilityapp.network;

import com.sm.sharemobilityapp.data.User
import com.sm.sharemobilityapp.domain.DataUser
import com.squareup.moshi.JsonClass;

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val users: List<NetworkUser>)

@JsonClass(generateAdapter = true)
data class NetworkUser(
    val id: Int,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val bonusPoints: Int = 0
)

fun NetworkUserContainer.asDomainModel(): List<DataUser> {
    return users.map {
        DataUser(
            id = it.id,
            type = it.type,
            username = it.username,
            password = it.password,
            firstname = it.firstname,
            lastname = it.lastname,
            address = it.address,
            bonusPoints = it.bonusPoints
        )
    }
}

fun NetworkUserContainer.asDataBaseModel(): List<User> {
    return users.map {
        User (
            id = it.id,
            type = it.type,
            username = it.username,
            password = it.password,
            firstname = it.firstname,
            lastname = it.lastname,
            address = it.address,
            bonusPoints = it.bonusPoints
                )
    }
}