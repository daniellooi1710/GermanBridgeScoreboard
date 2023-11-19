package com.germanbridgescoreboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.germanbridgescoreboard.databinding.ActivityMainBinding
import com.germanbridgescoreboard.ui.gameinit.InputPlayerRecyclerViewAdapter
import com.germanbridgescoreboard.ui.scoreboard.ScoreboardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewmodel : MainViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var db : PlayerDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTaskRoot) {
            val intent = intent
            val intentAction = intent.action
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null &&
                intentAction == Intent.ACTION_MAIN
            ) {
                finish()
                return
            }
        }

        viewmodel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedPref = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val gameOngoing = sharedPref.getBoolean("game_ongoing", false)

        db = Room.databaseBuilder(
            applicationContext,
            PlayerDatabase::class.java,
            getString(R.string.database_name)
        ).allowMainThreadQueries().build()

        if(gameOngoing) {
            val playerCount = sharedPref.getInt("player_count", 0)
            val currentRound = sharedPref.getInt("current_round", 0)
            val gamePlaying = sharedPref.getBoolean("game_playing", false)
            val gameEnded = sharedPref.getBoolean("game_ended", false)
            var gameprocess : MainViewModel.GAMEPROCESS

            viewmodel.playerNum.value = playerCount
            viewmodel.initGame()

            try{
                val playerRoundDao = db.playerRoundDao()
                val players = playerRoundDao.getPlayers()

                for(i in 0 until playerCount){
                    viewmodel.players[i] = players[i].name
                    viewmodel.total[i] = players[i].total
                    for (j in 0 until viewmodel.rounds){
                        val roundList = playerRoundDao.getPlayerRound(i, j)
                        val round = roundList[0]
                        viewmodel.playerBids[i][j] = round.bid
                        viewmodel.playerWins[i][j] = round.win
                        viewmodel.playerScoresT[j][i] = round.score
                    }
                }

                viewmodel.currentRound.value = currentRound

                gameprocess = if(gamePlaying) MainViewModel.GAMEPROCESS.PLAYING
                else if(gameEnded) MainViewModel.GAMEPROCESS.ENDED
                else MainViewModel.GAMEPROCESS.BIDDING

                viewmodel.gameProcess.value = gameprocess

                playerRoundDao.clearPlayerTable()
                playerRoundDao.clearRoundTable()
            }
            catch(e: Exception){
                viewmodel.newGame()
            }
        }
        else{
            viewmodel.gameProcess.value = MainViewModel.GAMEPROCESS.INIT
        }

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
        if(viewmodel.gameProcess.value != MainViewModel.GAMEPROCESS.INIT){
            with (sharedPref.edit()){
                putInt("player_count", viewmodel.playerCount)
                putInt("current_round", viewmodel.currentRound.value!!)
                putBoolean("game_ongoing", true)
                when(viewmodel.gameProcess.value){
                    MainViewModel.GAMEPROCESS.BIDDING -> {
                        putBoolean("game_playing", false)
                        putBoolean("game_ended", false)
                    }
                    MainViewModel.GAMEPROCESS.PLAYING -> {
                        putBoolean("game_playing", true)
                        putBoolean("game_ended", false)
                    }
                    MainViewModel.GAMEPROCESS.ENDED -> {
                        putBoolean("game_playing", false)
                        putBoolean("game_ended", true)
                    }
                    else -> {
                        // No Implementation
                    }
                }
                apply()
            }

            val playerDao = db.playerRoundDao()

            for(i in 0 until viewmodel.playerCount){
                val name = viewmodel.players[i]
                val total = viewmodel.total[i]
                val player = Player(i, name, total)
                playerDao.addPlayer(player)

                val bids = viewmodel.playerBids[i].clone()
                val wins = viewmodel.playerWins[i].clone()
                var scores = Array(viewmodel.rounds){0}
                for(j in 0 until viewmodel.rounds){
                    scores[j] = viewmodel.playerScoresT[j][i]
                }
                for (j in 0 until viewmodel.rounds){
                    val round = Round(j, i, bids[j], wins[j], scores[j])
                    playerDao.addPlayerRound(round)
                }
            }
        }
        else{
            with (sharedPref.edit()){
                putBoolean("game_ongoing", false)
                apply()
            }
        }
    }

}