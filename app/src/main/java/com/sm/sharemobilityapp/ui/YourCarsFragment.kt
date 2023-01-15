package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentYourCarsBinding
import com.sm.sharemobilityapp.ui.adapter.YoureCarListItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.*
import com.sm.sharemobilityapp.utils.GPSUtils
import kotlinx.coroutines.launch

class YourCarsFragment : Fragment() {
    private val carViewModel: CarViewModel by activityViewModels()
    private var viewModelAdapter: YoureCarListItemAdapter? = null
    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
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
        binding.viewModel = carViewModel
        viewModelAdapter = YoureCarListItemAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            if (response != null) {

                carViewModel.getCarsByUser(response.id!!)

                viewLifecycleOwner.lifecycleScope.launch {
                    carViewModel.carsByUser.collect() { cars ->
                        cars.apply {
                            viewModelAdapter?.cars = cars
                        }
                    }
                }

                view.findViewById<Button>(R.id.add_car).setOnClickListener {
                    view.findNavController().navigate(R.id.action_fragment_your_cars_to_fragment_add_car)
                }
            }
        }
    }

//    private var _binding: FragmentYourCarsBinding? = null
//    private val binding get() = _binding!!
//    private val carOwnerListViewModel: CarOwnerListViewModel by viewModels {
//        CarOwnerListViewModelFactory()
//    }
//
//    /*
//    *  The logged in userid is needed to get the cars from the API, which is saved in MAVM
//     */
//    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
//        MainActivityViewModelFactory()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentYourCarsBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val recyclerView = binding.recyclerView
//        mainActivityViewModel.userId.observe(viewLifecycleOwner) { id ->
//            carOwnerListViewModel.getCarsByOwner(id!!)
//        }
//        //carOwnerListViewModel.getCarsByOwnerTest()
//        carOwnerListViewModel.carData.observe(viewLifecycleOwner) { response ->
//            recyclerView.adapter = CarOwnerListItemAdapter(response)
//        }
//
//        recyclerView.setHasFixedSize(true)
//
//        binding.addCar.setOnClickListener {
//                view -> view.findNavController().navigate(R.id.fragment_add_car)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}