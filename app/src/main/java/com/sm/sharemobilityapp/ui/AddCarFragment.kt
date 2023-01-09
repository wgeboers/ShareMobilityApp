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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.sm.sharemobilityapp.BaseApplication
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.FragmentAddCarBinding
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModelFactory
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class AddCarFragment : Fragment() {
    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

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

        binding.addCarButton.setOnClickListener {
                view -> view.findNavController().navigate(R.id.action_fragment_add_car_to_fragment_your_cars)
        }
        binding.carPhoto.setImageDrawable(resources.getDrawable(R.drawable.ic_camera))
        binding.carCamera.setOnClickListener {
           // updatePhoto(photoName)
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
                    val scaledBitmap = getScaledBitmap(

                        photoFile.path,
                        80,
                        80
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


    fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
        // Read in the dimensions of the image on disk
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)

        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()

        // Figure out how much to scale down by
        val sampleSize = if (srcHeight <= destHeight && srcWidth <= destWidth) {

        } else {
            val heightScale = srcHeight / destHeight
            val widthScale = srcWidth / destWidth

            minOf(heightScale, widthScale).roundToInt()
        }

        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, BitmapFactory.Options().apply {
            inSampleSize = sampleSize as Int
        })
    }
        }
