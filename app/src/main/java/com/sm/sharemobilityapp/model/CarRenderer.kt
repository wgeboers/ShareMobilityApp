package com.sm.sharemobilityapp.model

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.sm.sharemobilityapp.BitmapHelper
import com.sm.sharemobilityapp.R


class CarRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Car>
) : DefaultClusterRenderer<Car>(context, map, clusterManager) {

    private val carIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(context,
            R.color.colorPrimary
        )
        BitmapHelper.vectorToBitmap(
            context,
            R.drawable.ic_car,
            color
        )
    }

    override fun onBeforeClusterItemRendered(
        item: Car,
        markerOptions: MarkerOptions
    ) {
        val location = LatLng(item.latitude, item.longitude)
        markerOptions.title(item.make)
            .position(location)
            .icon(carIcon)
    }

    override fun onClusterItemRendered(clusterItem: Car, marker: Marker) {
        marker.tag = clusterItem
    }

}