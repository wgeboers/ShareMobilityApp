package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json


data class UserInfo(

	@Json(name="id")
	val id: Int? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="username")
	val username: String? = null,

	@Json(name="password")
	val password: String? = null,

	@Json(name="firstname")
	val firstname: String? = null,

	@Json(name="lastname")
	val lastname: String? = null,

	@Json(name="address")
	val address: String? = null,

	@Json(name="bonusPoints")
	val bonuspoints: Int = 0

)
