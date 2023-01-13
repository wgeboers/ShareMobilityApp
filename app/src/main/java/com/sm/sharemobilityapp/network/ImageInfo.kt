package com.sm.sharemobilityapp.network

import com.squareup.moshi.Json

data class ImageInfo (
    @Json(name="imagePath")
    val imagePath: String
    )