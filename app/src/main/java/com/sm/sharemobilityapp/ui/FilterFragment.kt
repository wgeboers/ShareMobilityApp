package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.databinding.FragmentFilterBinding
import com.sm.sharemobilityapp.repository.DataRepository

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    // Datarepository testing
    private val DataRepository = DataRepository(SMRoomDatabase.getDatabase(BaseApplication()))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
        binding.filterModelAutocomplete.setAdapter(modelArrayAdapter)
        binding.filterRadiusAutocomplete.setAdapter(radiusArrayAdapter)

        binding.filterButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_filter_to_fragment_start)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}