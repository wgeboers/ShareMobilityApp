package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey()
    val id: Int?,
    val type: String? = null,
    val username: String? = null,
    val password: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val bonusPoints: Int? = 0
)
