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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding
import com.sm.sharemobilityapp.databinding.FragmentStartBinding
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_profile,
            container,
            false
        )

        binding.setLifecycleOwner(viewLifecycleOwner)
        binding.viewModel = userViewModel

        userViewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.youre_cars_button).setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_fragment_profile_to_fragment_your_cars)
        }
    }

    private fun onNetworkError() {
        if (!userViewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            userViewModel.onNetworkErrorShown()
        }
    }

//    private val viewModel: CarViewModel by lazy {
//        val activity = requireNotNull(this.activity){
//
//        }
//        ViewModelProvider(this, CarViewModel.CarViewModelFactory(activity.application))
//            .get(CarViewModel::class.java)
//    }
//
//    private var viewModelAdapter: ItemAdapter? = null
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
//            inflater,
//            R.layout.fragment_profile,
//            container,
//            false
//        )
//
//        binding.setLifecycleOwner(viewLifecycleOwner)
//        binding.viewModel = viewModel
//        viewModelAdapter = ItemAdapter()
//
//        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = viewModelAdapter
//        }
//
//        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
//            if (isNetworkError) onNetworkError()
//        })
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.cars.collect() {
//                    cars -> cars.apply {
//                viewModelAdapter?.cars = cars
//                }
//            }
//        }
//
//        view.findViewById<Button>(R.id.youre_cars_button).setOnClickListener{
//                view -> view.findNavController().navigate(R.id.action_fragment_profile_to_fragment_your_cars)
//        }
//    }
//
//    private fun onNetworkError() {
//        if (!viewModel.isNetworkErrorShown.value!!) {
//            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
//            viewModel.onNetworkErrorShown()
//        }
//    }
}