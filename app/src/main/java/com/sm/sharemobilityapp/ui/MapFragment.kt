package com.sm.sharemobilityapp.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.BitmapHelper
import com.sm.sharemobilityapp.ui.adapter.MarkerInfoWindowAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentMapBinding
import com.sm.sharemobilityapp.model.Car
import com.sm.sharemobilityapp.model.CarRenderer
import com.sm.sharemobilityapp.utils.GPSUtils
import com.sm.sharemobilityapp.utils.GPSUtils.hasPermission

class MapFragment : Fragment() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val cars: List<Car> by lazy {
        Datasource().loadCars()
    }

    private val lastKnownLocationIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(requireActivity(), R.color.lastKnownLocationIcon)
        BitmapHelper.vectorToBitmap(requireActivity(), R.drawable.ic_profile, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                cars.forEach { bounds.include(LatLng(it.latitude, it.longitude)) }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50))
            }
            addClusteredMarkers(googleMap)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myDataset = Datasource().loadCars()

        val amountOfResult: TextView = view.findViewById(R.id.start_filter_results_amount)
        amountOfResult.text = myDataset.size.toString()

        binding.filterButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_filter)
        }

        binding.mapButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_global_fragment_start)
        }

    }

    private fun addClusteredMarkers(googleMap: GoogleMap) {
        val clusterManager = ClusterManager<Car>(requireActivity(), googleMap)
        clusterManager.renderer =
            CarRenderer(
                requireActivity(),
                googleMap,
                clusterManager
            )
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireActivity()))
        clusterManager.addItems(cars)
        clusterManager.cluster()

        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach {it.alpha = 0.3f}
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }

        googleMap.setOnCameraIdleListener {
            clusterManager.markerCollection.markers.forEach {it.alpha = 1.0f}
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }

            clusterManager.onCameraIdle()
        }

        if (requireActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && requireActivity().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            GPSUtils.findDeviceLocation(requireActivity())
            if (GPSUtils.latitude != null && GPSUtils.latitude != null) {
                val marker = googleMap.addMarker(
                    MarkerOptions()
                        .title(getString(R.string.LastKnownLocationIconTitle))
                        .position(LatLng(GPSUtils.latitude!!, GPSUtils.longitude!!))
                        .icon(lastKnownLocationIcon)
                )
            }
        } else {
            AlertDialog.Builder(activity).setMessage(getString(R.string.NoLocationPermissionDescription)).setCancelable(false)
                .setPositiveButton(getString(R.string.Yes)) { _, _ ->
                    this.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton(getString(R.string.No)) { dialog, _ ->
                    dialog.cancel()
                }
                .create()
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
