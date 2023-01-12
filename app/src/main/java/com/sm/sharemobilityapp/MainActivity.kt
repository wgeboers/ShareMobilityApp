package com.sm.sharemobilityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.sm.sharemobilityapp.ui.LoginFragment
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModel
import com.sm.sharemobilityapp.ui.viewmodel.MainActivityViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val mainActivityViewModel : MainActivityViewModel by viewModels {
        MainActivityViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        mainActivityViewModel.loginSuccessful.observe(this) { result ->
            if(result == true) {
                binding.bottomNavigationView.menu.getItem(1).isVisible = true
                binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.ic_logout)
                binding.bottomNavigationView.menu.getItem(2).title = "Logout"
            } else {
                binding.bottomNavigationView.menu.getItem(1).isVisible = false
                binding.bottomNavigationView.menu.getItem(2).isVisible = true
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    navHostFragment.navController.navigate(R.id.action_global_fragment_start)
                }
                R.id.profile -> {
                    navHostFragment.navController.navigate(R.id.action_global_fragment_profile)
                }
                R.id.login -> {
                    navHostFragment.navController.navigate(R.id.action_global_fragment_login)
                }

            }
            true
        }
    }
}