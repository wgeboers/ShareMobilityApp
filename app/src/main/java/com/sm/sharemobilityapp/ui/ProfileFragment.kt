package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding
import com.sm.sharemobilityapp.network.UserInfo
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels {
        UserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        val recyclerView = binding.recyclerView
//        recyclerView.setHasFixedSize(true)

        binding.apply {
            viewModel = userViewModel
        }

        /*
         * Load profile of the logged user
         */
        userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                Toast.makeText(context, "$response", Toast.LENGTH_SHORT).show()
                binding.profileName.setText(response.firstname + " " + response.lastname)
                binding.profileAddress.setText(response.address)
                binding.profileEmail.setText(response.username)
                binding.profilePassword.setText(response.password)
            } else {
                Toast.makeText(context, "What the fuck?", Toast.LENGTH_SHORT).show()
                //findNavController().navigate(R.id.action_global_fragment_login)
            }
        }

        /*
         * Edit profile of the logged user
         * !!!ID AND TYPE HAVE TO BE NULL, BECAUSE ONCE CREATED THEY SHOULD NOT BE CHANGED!!!
         */
        binding.editButton.setOnClickListener {
            val userInfo = UserInfo(null,
                null,
                binding.profileEmail.text.toString(),
                binding.profilePassword.text.toString(),
                binding.profileName.text!!.split(" ")[0],
                binding.profileName.text!!.split(" ")[1],
                binding.profileAddress.text.toString()
            )
            userViewModel.updateUser(userInfo)
        }
        binding.yourCarsButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_your_cars)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}