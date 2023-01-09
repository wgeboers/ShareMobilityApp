package com.sm.sharemobilityapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String,
    val bonusPoints: Int = 0
)
