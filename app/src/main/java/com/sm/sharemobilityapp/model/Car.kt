package com.sm.sharemobilityapp.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class Car(
    val image: Int,
    val make: String,
    val model: String,
    val pricePerDay: Double,
    val totalPrice: Double,
    val rentedStartDate: String,
    val rentedEndDate: String,
    val latitude: Double,
    val longitude: Double,
    ) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(latitude, longitude)
    override fun getTitle(): String = make
    override fun getSnippet(): String = model
    }