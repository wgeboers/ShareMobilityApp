package com.sm.sharemobilityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.sm.sharemobilityapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //odnerstaande val loggedIn is tijdelijk voor het ontwikkelen
        val loggedIn = false
        if (loggedIn)  {
            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> {
                        navHostFragment.navController.navigate(R.id.action_global_fragment_start)
                    }
                    R.id.profile -> {
                        navHostFragment.navController.navigate(R.id.action_global_fragment_profile)
                    }
                }
                true
            }
        } else {
            binding.bottomNavigationView.setOnItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> {
                        navHostFragment.navController.navigate(R.id.action_global_fragment_start)
                    }
                    R.id.profile -> {
                        navHostFragment.navController.navigate(R.id.action_global_fragment_login)
                    }
                }
                true
            }
        }
    }
}