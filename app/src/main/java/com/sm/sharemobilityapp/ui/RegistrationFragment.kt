package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.MainActivity
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentRegistrationBinding
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.ui.viewmodel.RegistrationViewModel
import com.sm.sharemobilityapp.ui.viewmodel.RegistrationViewModelFactory
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var type: String

    private val registrationViewModel: RegistrationViewModel by activityViewModels {
        RegistrationViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registrationExistingaccount.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_login)
        }
        binding.userType.setOnCheckedChangeListener { _, checkedId ->
            type = if(checkedId == 0) {
                "CAR_OWNER"
            } else {
                "CAR_USER"
            }
        }

        binding.registrationRegistrationButton.setOnClickListener {
            val userInfo  = UserInfo(null,
            type,
            binding.registrationEmailEditText.text.toString(),
            binding.registrationPasswordEditText.text.toString(),
            binding.registrationFirstnameEditText.text.toString(),
            binding.registrationSecondnameEditText.text.toString(),
            binding.registrationAddressEditText.text.toString(),
            )
            registrationViewModel.registerUser(userInfo)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}