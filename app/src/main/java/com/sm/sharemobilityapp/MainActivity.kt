package com.sm.sharemobilityapp

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.sm.sharemobilityapp.ui.OverviewViewModel
import com.sm.sharemobilityapp.network.UserInfo

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

        binding.getUserID.setOnClickListener {
            //overviewViewModel.getUser(1L)
            //overviewViewModel.getLogin("TestCarUser2", "letmein2")
                val userInfo = UserInfo(
                    "CAR_USER",
                    null,
                    "User1",
                    "Letmein",
                    "Piet",
                    "Bab",
                    "Straat 2, 2987CP Alba",
                    0
                )
            overviewViewModel.postUserInfo(userInfo)
        }
    }
}
