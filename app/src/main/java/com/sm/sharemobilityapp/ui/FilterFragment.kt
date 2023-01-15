package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentFilterBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val carViewModel: CarViewModel by activityViewModels()

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

        binding.filterBrandAutocomplete.setAdapter(brandArrayAdapter)
        binding.filterBrandAutocomplete.setText(carViewModel.brandFilter.value, false)

        binding.filterModelAutocomplete.setAdapter(modelArrayAdapter)
        binding.filterModelAutocomplete.setText(carViewModel.modelFilter.value, false)

        binding.filterButton.setOnClickListener {
            setFilters(
                binding.filterBrandAutocomplete.text.toString().ifEmpty { null },
                binding.filterModelAutocomplete.text.toString().ifEmpty { null },
                binding.filterPriceFromAutocomplete.text.toString().toDoubleOrNull(),
                binding.filterPriceTillAutocomplete.text.toString().toDoubleOrNull()
            )
        }

        binding.removeFiltersButton.setOnClickListener {
            clearFilters()
        }
    }

    private fun setFilters(brand: String?, model: String?, priceFrom: Double?, priceTill: Double?) {
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