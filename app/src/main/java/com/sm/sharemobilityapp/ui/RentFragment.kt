package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentRentBinding

class RentFragment : Fragment() {
    private var _binding: FragmentRentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rentButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_global_fragment_start)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}