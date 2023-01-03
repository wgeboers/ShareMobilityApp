package com.sm.sharemobilityapp.domain

data class DataUser (
        val id: Int,
        val type: String,
        val username: String,
        val password: String,
        val firstname: String,
        val lastname: String,
        val address: String,
        val bonusPoints: Int = 0
        )