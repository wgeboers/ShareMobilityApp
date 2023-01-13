package com.sm.sharemobilityapp.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    val id: Int,
    val type: String,
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val address: String
)