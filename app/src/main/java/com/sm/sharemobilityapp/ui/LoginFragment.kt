package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModel
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModelFactory
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        //val loginButton = view.findViewById<View>(R.id.login_login_button)
        binding.apply {
            viewModel = userViewModel
        }

        binding.loginLoginButton.setOnClickListener {
            //login(binding.loginEmailEditText.text.toString(),binding.loginPasswordEditText.text.toString())
            //mainActivityViewModel.login(binding.loginEmailEditText.text.toString(),binding.loginPasswordEditText.text.toString())
            //use for easy login testing
            mainActivityViewModel.loginTest()
            mainActivityViewModel.userId.observe(viewLifecycleOwner) {
                if (it != null) {
                    userViewModel.getUser(it)
                } else {
                    Toast.makeText(context, "IT WORKS NOT", Toast.LENGTH_SHORT).show()
                }
            }
            //Temporary navigation on login...
            mainActivityViewModel.apiResponse.observe(viewLifecycleOwner) { response ->

                if(response.isNotEmpty() && response.equals("204")) {
                    Toast.makeText(context, "Wrong username/password", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.action_global_fragment_profile)
                }
            }
            //findNavController().navigate(R.id.action_global_fragment_profile)

        }

        binding.notYetAccountButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_registration)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}