package com.sm.sharemobilityapp.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.sm.sharemobilityapp.R

object GPSUtils {
    var longitude: Double? = null
        private set
    var latitude: Double? = null
        private set

    private const val REQUEST_LOCATION = 1

    private const val coarse = Manifest.permission.ACCESS_COARSE_LOCATION
    private const val fine = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissions = arrayOf(coarse, fine)

    private lateinit var locationManager: LocationManager

//    fun foregroundPermissionApproved(activity: Activity): Boolean {
//        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
//            activity,
//            android.Manifest.permission.ACCESS_FINE_LOCATION
//        )
//    }

    fun initPermissions(activity: Activity){
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_LOCATION)
    }

    fun findDeviceLocation(activity: Activity) {
        locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(GPS_PROVIDER)) {
            enableGps(activity)
        } else {
            getLocation(activity)
        }
    }

    private fun enableGps(activity: Activity) =
        AlertDialog.Builder(activity).setMessage(R.string.EnableGpsWarning).setCancelable(false)
            .setPositiveButton(R.string.Yes) { _, _ ->
                activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }
            .setNegativeButton(R.string.No) { dialog, _ ->
                dialog.cancel()
            }
            .create()
            .show()

    @SuppressLint("MissingPermission")
    private fun getLocation(activity: Activity) {
        if (!activity.hasPermission(coarse) && !activity.hasPermission(fine)) {
            initPermissions(activity)
        }

        val location = listOf(GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER,
            LocationManager.PASSIVE_PROVIDER
        )
            .map { locationManager.getLastKnownLocation(it) }
            .firstOrNull()

        when (location) {
            null -> Toast.makeText(activity, R.string.CannotDetermineLocation, Toast.LENGTH_SHORT).show()
            else -> {
                latitude = location.latitude
                longitude = location.longitude
            }
        }
    }

    fun Activity.hasPermission(permission: String) =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

}