package com.sm.sharemobilityapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sm.sharemobilityapp.MainActivity
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.adapter.RentedItemAdapter
import com.sm.sharemobilityapp.data.Datasource
import com.sm.sharemobilityapp.databinding.FragmentProfileBinding
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModel
import com.sm.sharemobilityapp.ui.viewmodel.UserViewModelFactory
import java.util.logging.Logger

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels {
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

        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)

        binding?.apply {
            viewModel = userViewModel
        }

        userViewModel.userInfo.observe(viewLifecycleOwner) { response ->
            if(response != null) {
                Toast.makeText(context, "${response}", Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_global_fragment_login)
            }
        }

//        val navController = findNavController()
//        val currentBackStackEntry = navController.currentBackStackEntry!!
//        val savedStateHandle = currentBackStackEntry.savedStateHandle
//        savedStateHandle.getLiveData<Boolean>(MainActivity.LOGIN_SUCCESSFUL)
//            .observe(currentBackStackEntry) { success ->
//                if(!success) {
//                    navController.navigate(R.id.fragment_login)
//                }
//            }


        binding.yourCarsButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_your_cars)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}