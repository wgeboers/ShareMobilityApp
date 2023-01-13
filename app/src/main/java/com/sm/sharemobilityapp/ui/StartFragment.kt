package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentStartBinding
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.utils.GPSUtils
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch

class StartFragment : Fragment() {
    private val carViewModel: CarViewModel by activityViewModels()
    private var viewModelAdapter: ItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentStartBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_start,
            container,
            false
        )

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = carViewModel
        viewModelAdapter = ItemAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        carViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GPSUtils.initPermissions(requireActivity())

        viewLifecycleOwner.lifecycleScope.launch {
            if (carViewModel.areFiltersSet()) {
                    carViewModel.filteredCars.collect() {
                        cars -> cars.apply {
                    viewModelAdapter?.cars = cars
                    view.findViewById<TextView>(R.id.start_filter_results_amount).text = carViewModel.filteredCars.count().toString()
                    }
                }
            } else {
                carViewModel.cars.collect() {
                        cars -> cars.apply {
                    viewModelAdapter?.cars = cars
                    view.findViewById<TextView>(R.id.start_filter_results_amount).text = carViewModel.cars.count().toString()
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.filter_button).setOnClickListener{
            view -> view.findNavController().navigate(R.id.action_fragment_start_to_fragment_filter)
        }

        view.findViewById<FloatingActionButton>(R.id.map_button).setOnClickListener{
            view -> view.findNavController().navigate(R.id.action_home_to_fragment_map)
        }
    }

    private fun onNetworkError() {
        if (!carViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            carViewModel.onNetworkErrorShown()
        }
    }
}