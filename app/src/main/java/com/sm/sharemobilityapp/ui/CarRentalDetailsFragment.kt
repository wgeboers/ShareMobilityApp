package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.databinding.FragmentCarRentalDetailsBinding
import com.sm.sharemobilityapp.databinding.OfferListItemBinding
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CarRentalDetailsFragment : Fragment() {
    private val carViewModel: CarViewModel by activityViewModels()
    private val args: CarRentalDetailsFragmentArgs by navArgs()
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
        super.onViewCreated(view, savedInstanceState)
        carViewModel.setIdFilter(args.carId)

        viewLifecycleOwner.lifecycleScope.launch {
            binding.carInfo = carViewModel.car.first().elementAtOrNull(0)
        }

        view.findViewById<Button>(R.id.rent_button).setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_fragment_car_rental_details_to_fragment_rent)
        }
    }
}

