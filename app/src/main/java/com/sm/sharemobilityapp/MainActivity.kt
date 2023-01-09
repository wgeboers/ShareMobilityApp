package com.sm.sharemobilityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.sm.sharemobilityapp.ui.LoginFragment


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //By default invis, because switching does not work...
        binding.bottomNavigationView.menu.getItem(1).isVisible = true
        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) {
                success ->
                if(success) {
                    binding.bottomNavigationView.menu.getItem(1).isVisible = true
                } else {
                    binding.bottomNavigationView.menu.getItem(2).isVisible = false
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