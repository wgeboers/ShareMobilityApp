package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.adapter.RentedItemAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myDataset = Datasource().loadCars()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = RentedItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        binding.youreCarsButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_your_cars)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}