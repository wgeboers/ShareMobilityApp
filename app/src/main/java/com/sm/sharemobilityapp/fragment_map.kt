package com.sm.sharemobilityapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.sm.sharemobilityapp.adapter.ItemAdapter
import com.sm.sharemobilityapp.adapter.MarkerInfoWindowAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentMapBinding
import com.sm.sharemobilityapp.model.Car
import com.sm.sharemobilityapp.model.CarRenderer

class fragment_map : Fragment() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val cars: List<Car> by lazy {
        Datasource().loadCars()
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
