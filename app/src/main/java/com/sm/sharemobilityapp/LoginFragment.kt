package com.sm.sharemobilityapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
import com.sm.sharemobilityapp.ui.UserViewModel
import com.sm.sharemobilityapp.ui.UserViewModelFactory
import com.sm.sharemobilityapp.data.*
import com.sm.sharemobilityapp.ui.OverviewViewModel
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.sm.sharemobilityapp.network.UserInfo
import android.util.Log

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"




/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameter
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginTextView: TextView
    lateinit var user: User

    private fun loginUser(username: String, password: String): UserInfo? {
        overviewViewModel.getLogin("TestCarUser2", "letmein2")
        Log.d("loginFragment", "loginUser called")
        Log.d("LoginFragment", overviewViewModel.userInfo.value?.firstname.toString())
        return overviewViewModel.userInfo.value
    }

    private val overviewViewModel: OverviewViewModel by viewModels()


    private val viewModel: UserViewModel by activityViewModels {
        UserViewModelFactory(
            (activity?.application as BaseApplication).database.userDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        overviewViewModel.status.observe(this) {
            binding.userinfoTxtView.text = overviewViewModel.userInfo.value.toString()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false,)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userInfoTextView = binding.userinfoTxtView
        val saveButton = binding.loginButton.setOnClickListener{
            var naam: String = "leeg"
            val username = binding.usernamETxt.text.toString()
            val password = binding.passwordETxt.text.toString()
            val userInfo  = loginUser(username, password)
            binding.userinfoTxtView.text = userInfo?.firstname.toString()
            Log.d("Naam waarde", naam)

            // Convert via Elvis operator?
            if (userInfo != null) {
                viewModel.addNewUser(userInfo)
            }

        }
    }


}