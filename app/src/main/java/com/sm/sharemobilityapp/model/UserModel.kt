package com.sm.sharemobilityapp.model

data class UserModel(
    val id: Int,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String
)