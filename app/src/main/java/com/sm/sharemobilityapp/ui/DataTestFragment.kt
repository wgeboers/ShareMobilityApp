//package com.sm.sharemobilityapp.ui
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.activityViewModels
//import com.sm.sharemobilityapp.databinding.FragmentLoginBinding
//import com.sm.sharemobilityapp.data.*
//import androidx.fragment.app.viewModels
//import com.sm.sharemobilityapp.network.UserInfo
//import android.util.Log
//import com.sm.sharemobilityapp.BaseApplication
//import com.sm.sharemobilityapp.ui.*
//import com.sm.sharemobilityapp.ui.viewmodel.*
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
//
//
//
///**
// * A simple [Fragment] subclass.
// * Use the [LoginFragment.newInstance] factory method to
// * create an instance of this fragment.
// */
//class LoginFragment : Fragment() {
//    // TODO: Rename and change types of parameter
//    private var _binding: FragmentLoginBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var loginTextView: TextView
//    lateinit var user: User
//
//    private fun loginUser(username: String, password: String): UserInfo? {
////        overviewViewModel.getLogin("TestCarUser2", "letmein2")
//        overviewViewModel.getLogin(username, password)
//        Log.d("loginFragment", "loginUser called")
//        Log.d("LoginFragment", overviewViewModel.userInfo.value?.firstname.toString())
//        return overviewViewModel.userInfo.value
//    }
//
//    private val overviewViewModel: OverviewViewModel by viewModels()
//
//
//    private val viewModel: UserViewModel by activityViewModels {
//        UserViewModelFactory(
//            (activity?.application as BaseApplication).database.userDao()
//        )
//    }
//
//    private val carViewModel: CarViewModel by activityViewModels {
//        CarViewModelFactory(
//            (activity?.application as BaseApplication).database.carDao()
//        )
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        overviewViewModel.status.observe(this) {
//            binding.userinfoTxtView.text = overviewViewModel.userInfo.value.toString()
//        }
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        _binding = FragmentLoginBinding.inflate(inflater, container, false,)
//        val view = binding.root
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val userInfoTextView = binding.userinfoTxtView
//        val saveButton = binding.loginButton.setOnClickListener{
//            var naam: String = "leeg"
//            val username = binding.usernamETxt.text.toString()
//            val password = binding.passwordETxt.text.toString()
//            val userInfo  = loginUser(username, password)
////            overviewViewModel.getCarbyId(1)
//            overviewViewModel.getCars()
//            val carInfo: String = overviewViewModel.carInfo.value.toString()
//            binding.userinfoTxtView.text = carInfo.toString()
//            binding.userinfoTxtView.text = userInfo?.firstname ?: "Geen gebruiker gevonden"
//            Log.d("Naam waarde", naam)
//
//            // Convert via Elvis operator?
//            if (userInfo != null) {
//                viewModel.addNewUser(userInfo)
//            }
//
//        }
//        val addCarButton = binding.addCarButton.setOnClickListener {
//            carViewModel.insertCar(Car(
//                id = 10,
//                licensePlate = "3-zjs-90",
//                carOwnerID = 3,
//                makeval = "BMW",
//                modelval = "8x",
//                mileageval = 8,
//                hourlyRate = 10.0,
//                longitude = 5.655555,
//                latitude = 6.77777,
//                termsOfPickup = "Snel",
//                termsOfReturn ="Graag heel",
//                purchasePriceval = 5,
//                amountOfYearsOwned  = 8,
//                usageCostsPerKm = 78.9,
//                totalCostOfOwnership = 90.0,
//            ))
//        }
//    }
//
//
//}