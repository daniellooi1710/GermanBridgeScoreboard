package com.example.germanbridgescoreboard

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.germanbridgescoreboard.databinding.ActivityMainBinding
import com.example.germanbridgescoreboard.ui.gameinit.InputPlayerRecyclerViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

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

        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        val adapter = InputPlayerRecyclerViewAdapter(viewmodel.playerCount)
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigate(R.id.navigation_home)
                return true
            }
            R.id.toolbarButton -> {
                var isNull = false
                for(i in 0..<(viewmodel.playerCount)){
                    viewmodel.players[i] = adapter.obtainName()[i]
                    if(viewmodel.players[i] == ""){
                        Toast.makeText(this.applicationContext, "Player names cannot be null", Toast.LENGTH_LONG).show()
                        isNull = true
                        break
                    }
                }
                if(!isNull){
                    viewmodel.startGame()
                    navController.navigate(R.id.navigation_home)
                }

                return true
            }
        }
        return onOptionsItemSelected(item)
    }

}