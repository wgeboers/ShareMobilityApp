package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.SMRoomDatabase
import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
import com.sm.sharemobilityapp.repository.DataRepository

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.notYetAccountButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_login_to_fragment_registration)
        }

        binding.loginLoginButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_login_to_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}