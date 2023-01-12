package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.adapter.CarOwnerListItemAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentYourCarsBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarOwnerListViewModel
import com.sm.sharemobilityapp.ui.viewmodel.CarOwnerListViewModelFactory
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModel
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModelFactory

class CarOwnerFragment : Fragment() {
    private var _binding: FragmentYourCarsBinding? = null
    private val binding get() = _binding!!
    private val carOwnerListViewModel: CarOwnerListViewModel by viewModels {
        CarOwnerListViewModelFactory()
    }

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYourCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        mainActivityViewModel.userId.observe(viewLifecycleOwner) { id ->
            carOwnerListViewModel.getCarsByOwner(id)
        }
        //carOwnerListViewModel.getCarsByOwnerTest()
        carOwnerListViewModel.carData.observe(viewLifecycleOwner) { response ->
                recyclerView.adapter = CarOwnerListItemAdapter(response)
        }

        recyclerView.setHasFixedSize(true)

        binding.addCar.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_add_car)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}