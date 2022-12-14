package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.adapter.YoureCarListItemAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentYourCarsBinding

class YourCarsFragment : Fragment() {
    private var _binding: FragmentYourCarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myDataset = Datasource().loadCars()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = YoureCarListItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        binding.addCar.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_your_cars_to_fragment_add_car)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}