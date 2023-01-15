package com.sm.sharemobilityapp.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfo(
    val id: Int? = null,
    val type: String? = null,
    val username: String? = null,
    val password: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val address: String? = null,
    val bonuspoints: Int = 0
)
