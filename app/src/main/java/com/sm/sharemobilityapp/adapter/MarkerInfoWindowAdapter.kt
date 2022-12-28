package com.sm.sharemobilityapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.model.Car

class MarkerInfoWindowAdapter( private val context: Context ) : GoogleMap.InfoWindowAdapter {
    override fun getInfoContents(marker: Marker): View? {
        val car = marker.tag as? Car ?: return null
        val ppd = "€ "+car.pricePerDay.toString()

        val view = LayoutInflater.from(context).inflate(R.layout.marker_info_contents, null)
        view.findViewById<TextView>( R.id.text_view_make).text = car.make
        view.findViewById<TextView>( R.id.text_view_model).text = car.model
        view.findViewById<TextView>( R.id.text_view_price).text = ppd

        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}