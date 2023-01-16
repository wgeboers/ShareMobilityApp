package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentFilterBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

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


        viewLifecycleOwner.lifecycleScope.launch {
            val brand = ArrayList<String>()

            val brandArrayAdapter = activity?.let {
                ArrayAdapter<String>(
                    it,
                    R.layout.dropwdown_item,
                    brand
                )
            }
            binding.filterBrandAutocomplete.setAdapter(brandArrayAdapter)
            binding.filterBrandAutocomplete.setText(carViewModel.brandFilter.value, false)

            carViewModel.brands.collect{
                brand.addAll(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val model = ArrayList<String>()

            val modelArrayAdapter = activity?.let {
                ArrayAdapter<String>(
                    it,
                    R.layout.dropwdown_item,
                    model
                )
            }

            binding.filterModelAutocomplete.setAdapter(modelArrayAdapter)
            binding.filterModelAutocomplete.setText(carViewModel.modelFilter.value, false)

            carViewModel.models.collect{
                model.addAll(it)
            }
        }

        binding.filterButton.setOnClickListener {
            if(binding.filterPriceFromAutocomplete.text.toString().ifEmpty { null } == null) {
                setFilters(
                    binding.filterBrandAutocomplete.text.toString().ifEmpty { null },
                    binding.filterModelAutocomplete.text.toString().ifEmpty { null },
                    "0.0",
                    "9999.00"
                )
            } else {
                setFilters(
                    binding.filterBrandAutocomplete.text.toString().ifEmpty { null },
                    binding.filterModelAutocomplete.text.toString().ifEmpty { null },
                    binding.filterPriceFromAutocomplete.text.toString().ifEmpty { null },
                    binding.filterPriceTillAutocomplete.text.toString().ifEmpty { null }
                )
            }
        }

        binding.removeFiltersButton.setOnClickListener {
            clearFilters()
        }
    }

    private fun setFilters(brand: String?, model: String?, priceFrom: String?, priceTill: String?) {
        carViewModel.setBrandFilter(brand)
        carViewModel.setModelFilter(model)
        carViewModel.setPriceFromFilter(priceFrom?.toDoubleOrNull())
        carViewModel.setPriceTillFilter(priceTill?.toDoubleOrNull())
        view?.findNavController()?.navigate(R.id.action_fragment_filter_to_fragment_start)
    }

    private fun clearFilters() {
        carViewModel.clearCityFilter()
        carViewModel.clearRadiusFilter()
        carViewModel.clearBrandFilter()
        carViewModel.clearModelFilter()
        carViewModel.clearPriceFromFilter()
        carViewModel.clearPriceTillFilter()
        view?.findNavController()?.navigate(R.id.action_fragment_filter_to_fragment_start)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}