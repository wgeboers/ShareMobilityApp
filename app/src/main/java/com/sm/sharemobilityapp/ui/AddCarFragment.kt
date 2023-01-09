package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentAddCarBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModelFactory

class AddCarFragment : Fragment() {
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private val carViewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as BaseApplication).database.carDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val type = resources.getStringArray(R.array.types)
        val fuel = resources.getStringArray(R.array.fuels)

        val typeArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                type
            )
        }

        val fuelArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                fuel
            )
        }

        binding.addCarTypeAutocomplete.setAdapter(typeArrayAdapter)
        binding.addCarFuelAutocomplete.setAdapter(fuelArrayAdapter)

        binding.addCarButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_add_car_to_fragment_your_cars)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}