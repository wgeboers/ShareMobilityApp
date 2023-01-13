package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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

    /*
    *  The logged in userid is needed to get the cars from the API, which is saved in MAVM
     */
    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentYourCarsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_your_cars,
            container,
            false
        )

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = viewModel
        viewModelAdapter = ItemAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

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

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}