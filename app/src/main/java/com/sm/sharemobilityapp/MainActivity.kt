package com.sm.sharemobilityapp

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.sm.sharemobilityapp.overview.OverviewViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val overviewViewModel: OverviewViewModel by viewModels()
        overviewViewModel.status.observe(this) {
            binding.resultTextView.text = overviewViewModel.status.value.toString()
        }

        binding.getUsersBtn.setOnClickListener {
            overviewViewModel.getUsers()
        }

        binding.getCarsBtn.setOnClickListener {
            overviewViewModel.getCars()
        }
    }
}
