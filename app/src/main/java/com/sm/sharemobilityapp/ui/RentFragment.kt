package com.sm.sharemobilityapp.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentRentBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.ReservationViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory
import com.sm.sharemobilityapp.utils.DateConverterUtils

class RentFragment : Fragment() {
    private val reservationViewModel: ReservationViewModel by activityViewModels()
    private val carViewModel: CarViewModel by activityViewModels()
    private var _binding: FragmentRentBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            viewModel = reservationViewModel
        }

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        binding.rentDatePicker.setOnClickListener {
            dateRangePicker.show(childFragmentManager, "datePickerDialog")
        }

        dateRangePicker.addOnPositiveButtonClickListener {
            reservationViewModel.setStartRentDate(DateConverterUtils.convertLongToDate(it.first))
            reservationViewModel.setEndRentDate(DateConverterUtils.convertLongToDate(it.second))
            reservationViewModel.setTotalPrice(carViewModel.carPricePerHour.value!!)
            view.findNavController().navigate(R.id.action_fragment_rent_self)
        }

        binding.rentButton.setOnClickListener {
            userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
                if(response != null) {
                    if (binding.rentTermsButton.isChecked) {
                        if (reservationViewModel.areDatesSet()) {
                            reservationViewModel.postReservation(
                                response.id!!,
                                carViewModel.idFilter.value!!,
                                DateConverterUtils.convertDateToDatetime(reservationViewModel.startRentDate.value!!),
                                DateConverterUtils.convertDateToDatetime(reservationViewModel.endRentDate.value!!)
                            )

                            reservationViewModel.clearStartRentDate()
                            reservationViewModel.clearEndRentDate()

                            reservationViewModel.clearTotalPrice()
                            view.findNavController().navigate(R.id.action_fragment_rent_to_home)
                        } else {
                            val toastDates =
                                Toast.makeText(context, "Graag datums selecteren", Toast.LENGTH_LONG)
                            toastDates.show()
                        }
                    } else {
                        val toastTerms = Toast.makeText(
                            context,
                            "Graag akkoord op de algemene voorwaarden",
                            Toast.LENGTH_LONG
                        )
                        toastTerms.show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}