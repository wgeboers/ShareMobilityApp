package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.reservation.Reservation
import com.sm.sharemobilityapp.databinding.FragmentRentBinding
import com.sm.sharemobilityapp.model.ReservationModel
import com.sm.sharemobilityapp.network.ReservationInfo
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.ReservationViewModel
import com.sm.sharemobilityapp.utils.DateConverterUtils

class RentFragment : Fragment() {
    private val reservationViewModel: ReservationViewModel by activityViewModels()
    private val carViewModel: CarViewModel by activityViewModels()
    private var _binding: FragmentRentBinding? = null
    private val binding get() = _binding!!

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

        dateRangePicker.addOnPositiveButtonClickListener{
            reservationViewModel.setStartRentDate(DateConverterUtils.convertLongToDate(it.first))
            reservationViewModel.setEndRentDate(DateConverterUtils.convertLongToDate(it.second))
            view.findNavController().navigate(R.id.action_fragment_rent_self)
        }

        binding.rentButton.setOnClickListener {
            if (binding.rentTermsButton.isChecked) {
                if (reservationViewModel.areDatesSet()) {
                    reservationViewModel.postReservation(16,carViewModel.idFilter.value!!, reservationViewModel.startRentDate.value!!, reservationViewModel.endRentDate.value!!)

                    reservationViewModel.clearStartRentDate()
                    reservationViewModel.clearEndRentDate()

                    view.findNavController().navigate(R.id.action_fragment_rent_to_home)
                } else {
                    val toastDates = Toast.makeText(context, "Graag datums selecteren", Toast.LENGTH_LONG)
                    toastDates.show()
                }
            } else {
                val toastTerms = Toast.makeText(context, "Graag akkoord op de algemene voorwaarden", Toast.LENGTH_LONG)
                toastTerms.show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}