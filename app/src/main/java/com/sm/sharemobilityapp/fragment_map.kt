package com.sm.sharemobilityapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.sm.sharemobilityapp.adapter.MarkerInfoWindowAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentMapBinding
import com.sm.sharemobilityapp.model.Car
import com.sm.sharemobilityapp.model.CarRenderer

class fragment_map : Fragment() {
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
            addClusteredMarkers(googleMap)
            //addMarkers(googleMap)
            //googleMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireActivity()))
        }

        return view
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

        googleMap.setOnCameraIdleListener {
            clusterManager.onCameraIdle()
        }
    }

//    private fun addMarkers(googleMap: GoogleMap) {
//        cars.forEach{ car ->
//            val location = LatLng(car.latitude, car.longitude)
//            val marker = googleMap.addMarker(
//                MarkerOptions()
//                    .title(car.make+" "+car.model)
//                    .position(location)
//                    .icon(carIcon)
//            )
//            marker?.tag = car
//        }
//    }

//    private val carIcon: BitmapDescriptor by lazy {
//        val color = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)
//        BitmapHelper.vectorToBitmap(requireActivity(), R.drawable.ic_car, color)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
