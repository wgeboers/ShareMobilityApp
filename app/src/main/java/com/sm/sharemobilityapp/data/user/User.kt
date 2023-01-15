package com.sm.sharemobilityapp.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sm.sharemobilityapp.model.UserModel

@Entity
data class User constructor(
    @PrimaryKey val id: Int,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val bonusPoints: Int = 0
)

fun List<User>.asDomainModel(): List<UserModel> {
    return map {
        UserModel(
            id = it.id,
            type = it.type,
            username = it.username,
            password = it.password,
            firstname = it.firstname,
            lastname = it.lastname,
            address = it.address
        )
    }
}