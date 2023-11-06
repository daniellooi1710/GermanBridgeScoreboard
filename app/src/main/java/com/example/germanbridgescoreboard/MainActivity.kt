package com.example.germanbridgescoreboard

import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.germanbridgescoreboard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_bids_outcomes, R.id.navigation_scoreboard
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        if(navController.currentDestination?.id == R.id.navigation_game_init){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        navView.setupWithNavController(navController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigate(R.id.action_navigation_game_init_to_navigation_home)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}