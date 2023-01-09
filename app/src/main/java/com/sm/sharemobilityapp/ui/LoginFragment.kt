package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory

class LoginFragment : Fragment() {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
    }
    private lateinit var savedStateHandle: SavedStateHandle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        savedStateHandle = findNavController().previousBackStackEntry!!.savedStateHandle
        savedStateHandle[LOGIN_SUCCESSFUL] = false

        //val loginButton = view.findViewById<View>(R.id.login_login_button)
        binding.apply {
            viewModel = userViewModel
        }

        binding.loginLoginButton.setOnClickListener {
            //login(binding.loginEmailEditText.text.toString(),binding.loginPasswordEditText.text.toString())
            userViewModel.login(binding.loginEmailEditText.text.toString(),binding.loginPasswordEditText.text.toString())
            //Temporary navigation on login...
            userViewModel.apiResponse.observe(viewLifecycleOwner) { response ->

                if(response.isNotEmpty() && response.equals("204")) {
                    Toast.makeText(context, "Wrong username/password", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(context, response, Toast.LENGTH_SHORT).show()
                    savedStateHandle[LOGIN_SUCCESSFUL] = true
                    findNavController().navigate(R.id.action_global_fragment_profile)
                }
            }
            //findNavController().navigate(R.id.action_global_fragment_profile)

        }

        binding.notYetAccountButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_registration)
        }
    }

    //Deprecated...
    //DONT OBSERVE USERINFO, USE API RESPONSE TO CRAFT WHAT YOU NEED IN FRONT END
    private fun login(username: String, password: String) {
        userViewModel.login(username, password)
        userViewModel.userInfo.observe(viewLifecycleOwner) { result ->
            binding.loginLoginButton.text = result.id.toString()
        }

    }

    fun showErrorMessage() {
        // do something
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}