package com.sm.sharemobilityapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.databinding.FragmentCarRentalDetailsBinding

class fragment_car_rental_details : Fragment() {
    private var _binding: FragmentCarRentalDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarRentalDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rentButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_rent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}