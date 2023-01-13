package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentStartBinding
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.databinding.FragmentYourCarsBinding
import kotlinx.coroutines.launch

class YourCarsFragment : Fragment() {
    private val viewModel: CarViewModel by lazy {
        val activity = requireNotNull(this.activity){

        }
        ViewModelProvider(this, CarViewModel.CarViewModelFactory(activity.application))
            .get(CarViewModel::class.java)
    }

    private var viewModelAdapter: ItemAdapter? = null

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
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cars.collect() {
                    cars -> cars.apply {
                viewModelAdapter?.cars = cars
                }
            }
        }

        view.findViewById<Button>(R.id.add_car).setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_fragment_your_cars_to_fragment_add_car)
        }
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}