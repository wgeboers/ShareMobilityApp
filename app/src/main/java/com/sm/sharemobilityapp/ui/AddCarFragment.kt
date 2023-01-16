package com.sm.sharemobilityapp.ui

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentAddCarBinding
import com.sm.sharemobilityapp.databinding.FragmentCarRentalDetailsBinding
import com.sm.sharemobilityapp.databinding.FragmentStartBinding
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.ImageInfo
import com.sm.sharemobilityapp.ui.adapter.ItemAdapter
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModel
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModelFactory
import com.sm.sharemobilityapp.utils.GPSUtils
import com.sm.sharemobilityapp.utils.GPSUtils.hasPermission
import com.sm.sharemobilityapp.utils.GPSUtils.longitude
import com.sm.sharemobilityapp.utils.PhotoUtils
import java.io.File
import java.util.*

class AddCarFragment : Fragment() {
    private val carViewModel: CarViewModel by activityViewModels()
    private var viewModelAdapter: ItemAdapter? = null
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private val mainActivityViewModel: MainActivityViewModel by activityViewModels {
        MainActivityViewModelFactory()
    }

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { didTakePhoto: Boolean ->
        if (didTakePhoto && photoName != null) {
            // TO DO: place filename in image?
            updatePhoto(photoName)
        }
    }

    private var photoName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        PhotoUtils.initPermissions(requireActivity())

        val type = resources.getStringArray(R.array.types)
        val fuel = resources.getStringArray(R.array.fuels)

        val typeArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                type
            )
        }

        val fuelArrayAdapter = activity?.let {
            ArrayAdapter<String>(
                it,
                R.layout.dropwdown_item,
                fuel
            )
        }

        binding.addCarTypeAutocomplete.setAdapter(typeArrayAdapter)
        binding.addCarFuelAutocomplete.setAdapter(fuelArrayAdapter)

//        binding.addCarButton.setOnClickListener { view ->
//            view.findNavController().navigate(R.id.action_fragment_add_car_to_fragment_your_cars)
//        }

        binding.carCamera.setOnClickListener {
            if (requireActivity().hasPermission(Manifest.permission.CAMERA)) {
                photoName = "IMG_${Date()}.JPG"
                val photoFile = File(requireContext().applicationContext.filesDir, photoName)
                val photoUri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.sm.sharemobilityapp.fileprovider",
                    photoFile
                )
                takePhoto.launch(photoUri)
            } else {
                val toast =
                    Toast.makeText(context, getString(R.string.NoCameraPermissionDescription), Toast.LENGTH_LONG)
                toast.show()
            }
        }

        binding.addCarButton.setOnClickListener {
            if (!checkInputFields()) {
                insertCar()
                //view.findNavController().navigate(R.id.action_fragment_add_car_to_profile)
            }
        }

        carViewModel.carinfo.observe(viewLifecycleOwner) { newCar ->
            val userId = mainActivityViewModel.userId.value?.toInt() ?: -1
            if (userId != -1) {
                carViewModel.insertRegistration(newCar.id!!, userId)
            } else {
                showToast(getString(R.string.UserNotFound))
            }
        }

        carViewModel.isNetworkMessage.observe(viewLifecycleOwner) { text ->
            if (text.get(0) == '5') {
                showToast(getString(R.string.SomethingWentWrongTryAgainLater))
            } else if (text.get(0) == '4') {
                showToast(getString(R.string.SomethingWentWrongCheckInput))
            } else if (text.get(0) != '2') {
                showToast(getString(R.string.SomethingWentWrongCheckInput))
            }
        }

        carViewModel.isNetworkMessageRegistration.observe(viewLifecycleOwner) { text ->
            if (text.get(0) == '5') {
                showToast(getString(R.string.SomethingWentWrongTryAgainLater))
            } else if (text.get(0) == '4') {
                showToast(getString(R.string.SomethingWentWrongCheckInput))
            } else if (text.get(0) == '2') {
                showToast(getString(R.string.RegistrationSuccesvol))
                view.findNavController().navigate(R.id.action_fragment_add_car_to_profile)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updatePhoto(photoFileName: String?) {
        if (binding.carPhoto.tag != photoFileName) {
            val photoFile = photoFileName?.let {
                File(requireContext().applicationContext.filesDir, it)
            }

            if (photoFile?.exists() == true) {
                binding.carPhoto.doOnLayout { measuredView ->
                    val scaledBitmap = PhotoUtils.getScaledBitmap(
                        photoFile.path,
                        binding.carPhoto.width,
                        binding.carPhoto.height
                    )
                    binding.carPhoto.setImageBitmap(scaledBitmap)
                    binding.carPhoto.tag = photoFileName
                }
            } else {
                binding.carPhoto.setImageBitmap(null)
                binding.carPhoto.tag = null
            }
        }
    }

    private fun insertCar() {
        val address = binding.addCarAddressAutocomplete.text.toString()
        val zipcode = binding.addCarZipcodeAutocomplete.text.toString()
        val city = binding.addCarCityAutocomplete.text.toString()

        val fullAddress = address+", "+zipcode+", "+city

        val latLng = GPSUtils.getLatLonFromAdress(requireActivity(),fullAddress)

        val longitude = latLng!![1]
        val latitude = latLng!![0]

        carViewModel.insertCar(
            CarInfo(
                type = binding.addCarTypeAutocomplete.text.toString(),
                licensePlate = binding.addCarLicensePlateAutocomplete.text.toString(),
                carOwner = null,
                make = binding.addCarMakeAutocomplete.text.toString(),
                model = binding.addCarModelAutocomplete.text.toString(),
                mileage = binding.addCarKmAutocomplete.text.toString().toInt(),
                hourlyRate = binding.addCarPricePerHourAutocomplete.text.toString().toDouble(),
                longitude = longitude,
                latitude = latitude,
                termsOfPickup = binding.addCarPickuptermsAutocomplete.text.toString(),
                termsOfReturn = binding.addCarReturntermsAutocomplete.text.toString(),
                purchasePrice = binding.addCarValueAutocomplete.text.toString().toInt(),
                amountOfYearsOwned = binding.addCarYearsOwnedAutocomplete.text.toString().toInt(),
                usageCostsPerKm = 3.0,
                totalCostOfOwnership = 6.0,
                fuelType = binding.addCarFuelAutocomplete.text.toString(),
                carImages = listOf(
                    ImageInfo(
                        imagePath = photoName.toString()
                    )
                )
            )
        )
    }

    private fun checkInputFields() : Boolean {
        val errorText = "This field is required"
        var isEmpty = false

        if (binding.addCarMakeAutocomplete.text!!.isEmpty()) {
            binding.addCarMakeAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarModelAutocomplete.text!!.isEmpty()) {
            binding.addCarModelAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarLicensePlateAutocomplete.text!!.isEmpty()) {
            binding.addCarLicensePlateAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarKmAutocomplete.text!!.isEmpty()) {
            binding.addCarKmAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarTypeAutocomplete.text!!.isEmpty()) {
            binding.addCarTypeAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarFuelAutocomplete.text!!.isEmpty()) {
            binding.addCarFuelAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarYearsOwnedAutocomplete.text!!.isEmpty()) {
            binding.addCarYearsOwnedAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarPickuptermsAutocomplete.text!!.isEmpty()) {
            binding.addCarPickuptermsAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarReturntermsAutocomplete.text!!.isEmpty()) {
            binding.addCarReturntermsAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarValueAutocomplete.text!!.isEmpty()) {
            binding.addCarValueAutocomplete.setError(errorText)
            isEmpty = true
        }
        if (binding.addCarPricePerHourAutocomplete.text!!.isEmpty()) {
            binding.addCarPricePerHourAutocomplete.setError(errorText)
            isEmpty = true
        }
        return isEmpty
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(activity?.application?.applicationContext, message, Toast.LENGTH_LONG)
        toast.show()
    }
}
