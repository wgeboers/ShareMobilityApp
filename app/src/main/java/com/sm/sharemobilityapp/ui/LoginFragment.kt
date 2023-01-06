package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    companion object {
        const val LOGIN_SUCCESSFUL: String = "LOGIN_SUCCESSFUL"
    }
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels() {
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


        binding.loginLoginButton.setOnClickListener {
            val username = binding.loginEmailEditText.text.toString()
            val password = binding.loginPasswordEditText.text.toString()
            login(username, password)

        }

        binding.notYetAccountButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_registration)
        }
    }

    private fun login(username: String, password: String) {
        userViewModel.getLogin(username, password)
        userViewModel.userInfo.observe(viewLifecycleOwner) { result ->
            if (result !== null) {
                savedStateHandle[LOGIN_SUCCESSFUL] = true
                findNavController().popBackStack()
            } else {
                showErrorMessage()
            }
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