package com.sm.sharemobilityapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.maps.android.clustering.ClusterManager
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.BitmapHelper
import com.sm.sharemobilityapp.databinding.FragmentMapBinding
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.model.CarRenderer
import com.sm.sharemobilityapp.ui.adapter.MarkerInfoWindowAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.utils.GPSUtils
import com.sm.sharemobilityapp.utils.GPSUtils.hasPermission
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MapFragment : Fragment() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by activityViewModels()

    private val lastKnownLocationIcon: BitmapDescriptor by lazy {
        val color = ContextCompat.getColor(requireActivity(), R.color.lastKnownLocationIcon)
        BitmapHelper.vectorToBitmap(requireActivity(), R.drawable.ic_profile, color)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            googleMap.setOnMapLoadedCallback {
                if (GPSUtils.latitude != null && GPSUtils.latitude != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(GPSUtils.latitude!!, GPSUtils.longitude!!), 15.0f))
                }
            }
            addClusteredMarkers(googleMap)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.filter_button).setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_fragment_map_to_fragment_filter)
        }

        view.findViewById<FloatingActionButton>(R.id.map_button).setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_fragment_map_to_home)
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun addClusteredMarkers(googleMap: GoogleMap) {
        val clusterManager = ClusterManager<CarModel>(requireActivity(), googleMap)
        clusterManager.renderer =
            CarRenderer(
                requireActivity(),
                googleMap,
                clusterManager
            )
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireActivity()))

        viewLifecycleOwner.lifecycleScope.launch {
            if (carViewModel.areFiltersSet()) {
                carViewModel.viewModelScope.launch {
                    carViewModel.filteredCars.collect {clusterManager.addItems(it)}
                }
            } else {
            carViewModel.viewModelScope.launch {
                carViewModel.cars.collect {clusterManager.addItems(it)}
                }
            }
        }

        carViewModel.viewModelScope.launch {
            carViewModel.cars.collect {clusterManager.addItems(it)}
        }

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

        if (requireActivity().hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) && requireActivity().hasPermission(
                Manifest.permission.ACCESS_FINE_LOCATION)) {
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
}
