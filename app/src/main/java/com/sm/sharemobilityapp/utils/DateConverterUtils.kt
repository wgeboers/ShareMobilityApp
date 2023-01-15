package com.sm.sharemobilityapp.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateConverterUtils {

    fun convertLongToDate(date: Long): String {
        val date = Date(date)
        //val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val format = SimpleDateFormat("yyyy-MM-dd")

        return format.format(date)
    }

    fun convertDateToDatetime(date: String): String {
        val dateTime = date+"T01:00:00"
        println(dateTime)
        return dateTime
    }
}
