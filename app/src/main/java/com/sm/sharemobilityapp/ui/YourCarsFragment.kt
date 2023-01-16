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
import com.sm.sharemobilityapp.ui.adapter.YourCarListItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.*
import kotlinx.coroutines.launch

class YourCarsFragment : Fragment() {
    private val carViewModel: CarViewModel by activityViewModels()
    private var viewModelAdapter: YourCarListItemAdapter? = null
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
        viewModelAdapter = YourCarListItemAdapter()

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
}