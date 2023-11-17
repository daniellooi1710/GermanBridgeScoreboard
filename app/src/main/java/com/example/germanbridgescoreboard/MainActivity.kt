package com.example.germanbridgescoreboard

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
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
    val viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
    val sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    val gameOngoing = sharedPref.getBoolean("game_ongoing", false)
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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.navigation_game_init) {

                navView.visibility = View.GONE
            } else {

                navView.visibility = View.VISIBLE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        //val viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        val adapter = InputPlayerRecyclerViewAdapter(viewmodel.playerCount)
        when (item.itemId) {
            android.R.id.home -> {
                navController.navigate(R.id.navigation_home)
                viewmodel.newGame()
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
                    adapter.resetArray()
                    viewmodel.startGame()
                    navController.navigate(R.id.navigation_home)
                }

                return true
            }
        }
        return onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        if(viewmodel.gameStarted.value == true){
            with (sharedPref.edit()){
                putInt("player_count", viewmodel.playerCount)
                putInt("rounds", viewmodel.rounds)
                putInt("current_round", viewmodel.currentRound.value!!)
                putBoolean("game_ongoing", true)
                if(viewmodel.gameProcess.value == MainViewModel.GAMEPROCESS.BIDDING) putBoolean("game_playing", false) else putBoolean("game_playing", true)
                apply()
            }
        }
    }

}