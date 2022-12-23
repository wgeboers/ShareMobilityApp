package com.sm.sharemobilityapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.databinding.FragmentFilterBinding

class fragment_filter : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

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
        binding.filterModelAutocomplete.setAdapter(modelArrayAdapter)

        binding.filterButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_global_fragment_start)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}