package com.sm.sharemobilityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navHostFragment : NavHostFragment
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        //Voor het opbouwen van de navbar
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)


        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //navbar functionality starts here:
        navView.setNavigationItemSelectedListener {
            it.isChecked =  true
            drawerLayout.closeDrawers()

            when(it.itemId){
                R.id.nav_home -> {
                    navHostFragment.navController.navigate(R.id.action_global_startFragment)
                }
                R.id.nav_login -> {
                    navHostFragment.navController.navigate(R.id.action_global_loginFragment)
                }
                R.id.nav_profile -> {
                    navHostFragment.navController.navigate(R.id.action_global_profileFragment)
                }
                R.id.nav_logout -> {
                    navHostFragment.navController.navigate(R.id.action_global_loginFragment)
                }

            }
            false
        }
    }

    //Benodigd voor de navbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}