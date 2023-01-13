package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.databinding.FragmentFilterBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModelFactory
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory
import java.util.concurrent.TimeUnit

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by activityViewModels()

    private val carViewModel: CarViewModel by viewModels {
        CarViewModelFactory(
            (activity?.application as BaseApplication).database.carDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = carViewModel
        }

        val brands = resources.getStringArray(R.array.brands)
        val model = resources.getStringArray(R.array.model)
        val radius = resources.getStringArray(R.array.radius)

        val brandArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                brands
            )
        }

        val modelArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                model
            )
        }

        val radiusArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                radius
            )
        }

        binding.filterBrandAutocomplete.setAdapter(brandArrayAdapter)
        binding.filterBrandAutocomplete.setText(carViewModel.brandFilter.value, false)

        binding.filterModelAutocomplete.setAdapter(modelArrayAdapter)
        binding.filterModelAutocomplete.setText(carViewModel.modelFilter.value, false)

        binding.filterRadiusAutocomplete.setAdapter(radiusArrayAdapter)
        binding.filterRadiusAutocomplete.setText(carViewModel.radiusFilter.value, false)

        binding.filterButton.setOnClickListener {
                setFilters(
                    //binding.filterCityAutocomplete.text.toString().ifEmpty { null },
                    //binding.filterRadiusAutocomplete.text.toString().ifEmpty { null },
                    binding.filterBrandAutocomplete.text.toString().ifEmpty { null },
                    binding.filterModelAutocomplete.text.toString().ifEmpty { null },
                    binding.filterPriceFromAutocomplete.text.toString().toDoubleOrNull(),
                    binding.filterPriceTillAutocomplete.text.toString().toDoubleOrNull()
                )
        }

        binding.removeFiltersButton.setOnClickListener{
            clearFilters()
        }
    }

    private fun setFilters(brand:String?, model: String?, priceFrom: Double?, priceTill: Double?) {
        //city?.let { carViewModel.setCityFilter(it) }
        //radius?.let { carViewModel.setRadiusFilter(it) }
        carViewModel.setBrandFilter(brand)
        carViewModel.setModelFilter(model)
        carViewModel.setPriceFromFilter(priceFrom)
        carViewModel.setPriceTillFilter(priceTill)
        view?.findNavController()?.navigate(R.id.action_fragment_filter_to_fragment_start)
    }

    private fun clearFilters() {
        carViewModel.clearCityFilter()
        carViewModel.clearRadiusFilter()
        carViewModel.clearBrandFilter()
        carViewModel.clearModelFilter()
        carViewModel.clearPriceFromFilter()
        carViewModel.clearPriceTillFilter()
        view?.findNavController()?.navigate(R.id.action_fragment_filter_self)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}