package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sm.sharemobilityapp.domain.DataUser

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val bonusPoints: Int = 0
)

fun List<User>.asDomainModel(): List<DataUser> {
    return map {
        DataUser (
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