package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding
import com.sm.sharemobilityapp.ui.adapter.RentedItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.*
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val reservationViewModel: ReservationViewModel by activityViewModels()
    private var viewModelAdapter: RentedItemAdapter? = null
    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
    }

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory()
    }

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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = reservationViewModel
        viewModelAdapter = RentedItemAdapter()

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                view.findViewById<TextView>(R.id.first_name).text = response.firstname
                view.findViewById<TextView>(R.id.last_name).text = response.lastname
                view.findViewById<TextView>(R.id.address).text = response.address

                if(response.type == "CAR_USER") {
                    reservationViewModel.refreshDataFromRepository()
                    reservationViewModel.getReservationsByUser(response.id!!)

                    viewLifecycleOwner.lifecycleScope.launch {
                        reservationViewModel.reservationsByUser.collect() { reservations ->
                            reservations.apply {
                                if (reservations != null) {
                                    viewModelAdapter?.reservations = reservations
                                }
                            }
                        }
                    }
                }

                val changeProfileButton: ImageButton = view.findViewById(R.id.change_profile_button)
                changeProfileButton.setOnClickListener {
                    view.findNavController().navigate(R.id.action_profile_to_editProfileFragment)
                }

                val logoutButton: ImageButton = view.findViewById(R.id.log_out_button)
                logoutButton.setOnClickListener {
                    mainActivityViewModel.logout()
                    view.findNavController().navigate(R.id.action_profile_to_fragment_login)
                }

                view.findViewById<Button>(R.id.your_cars_button).setOnClickListener {
                    view.findNavController().navigate(R.id.action_fragment_profile_to_fragment_your_cars)
                }
            }
        }
    }
}