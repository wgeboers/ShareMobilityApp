package com.sm.sharemobilityapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverterUtils {

    fun convertLongToDate(date: Long): String {
        val date = Date(date)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        return format.format(date)
    }

}
