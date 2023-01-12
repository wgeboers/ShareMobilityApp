package com.sm.sharemobilityapp.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.Car
import com.sm.sharemobilityapp.databinding.FragmentAddCarBinding
import com.sm.sharemobilityapp.network.CarInfo
import com.sm.sharemobilityapp.network.ImageInfo
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModelFactory
import com.sm.sharemobilityapp.utils.PhotoUtils
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class AddCarFragment : Fragment() {
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!
    private val userId = 1;

    private val carViewModel: CarViewModel by activityViewModels {
        CarViewModelFactory(
            (activity?.application as BaseApplication).database.carDao()
        )
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

        binding.addCarButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_fragment_add_car_to_fragment_your_cars)
        }

        binding.carCamera.setOnClickListener {
            photoName = "IMG_${Date()}.JPG"
            val photoFile = File(requireContext().applicationContext.filesDir, photoName)
            val photoUri = FileProvider.getUriForFile(
                requireContext(),
                "com.sm.sharemobilityapp.fileprovider",
                photoFile
            )
            takePhoto.launch(photoUri)
            Log.d("Fotofile", photoFile.toString())
        }
        binding.addCarButton.setOnClickListener {
            insertCar()
        }

        carViewModel.carinfo.observe(viewLifecycleOwner) { newCar ->
            Log.d("ADDCAR", newCar.id.toString())
            carViewModel.insertRegistration(newCar.id!!, userId)
        }

        carViewModel.isNetworkMessage.observe(viewLifecycleOwner) { text ->
            Log.d("AddCAR", text)
            showToast(text)
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
                    val scaledBitmap = PhotoUtils().getScaledBitmap(

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

        // For testing, values need binding
        val ownerID = 2
        val longitude = 0.0
        val latitude = 0.0
        val termsOfPickup = "Filler"
        val termsOfReturn = "Filler return"
        val usageCostPerKm = 10.0
        val totalCostOfOwnership = 3000.0

        carViewModel.insertCar(
            CarInfo(
                type = "ICE",
                licensePlate = "6-DFG-21",
                carOwner = null,
                make = "Hyundai",
                model = "i20",
                mileage = 10,
                hourlyRate = 9.0,
                longitude = longitude,
                latitude = latitude,
                termsOfPickup = termsOfPickup,
                termsOfReturn = termsOfReturn,
                purchasePrice = 10,
                amountOfYearsOwned = 10,
                usageCostsPerKm = usageCostPerKm,
                totalCostOfOwnership = totalCostOfOwnership,
                carImages = listOf(
                    ImageInfo(
                        imagePath = photoName.toString()
                    )
                )
            )
        )
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(activity?.application?.applicationContext, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}
