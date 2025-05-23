package com.germanbridgescoreboard

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.floor

data class Player(
    val name: String,
    val bids: MutableList<Int> = mutableListOf(),
    val wins: MutableList<Int> = mutableListOf(),
    val scores: MutableList<Int> = mutableListOf()
){
    val total: Int get() = scores.sum()
}

class MainViewModel : ViewModel() {
    enum class GAMEPROCESS{
        INIT,
        BIDDING,
        PLAYING,
        ENDED
    }

    var players: MutableList<Player> = mutableListOf()

    var gameProcess by mutableStateOf(GAMEPROCESS.INIT)
    var currentRound by mutableStateOf(0)

    val playerCount: Int get() = players.size
    var rounds: Int = 0

    private val _numPlayers = mutableStateOf(2)
    val numPlayers: State<Int> = _numPlayers

    fun increasePlayers() {
        _numPlayers.value = (_numPlayers.value + 1).coerceAtMost(12)
    }

    fun decreasePlayers() {
        _numPlayers.value = (_numPlayers.value - 1).coerceAtLeast(2)
    }

    /**
     * Initialize the game.
     *
     * Inserts all names of players, calculate number of rounds, and initializes score arrays.
     */
    fun initGame(names: List<String>){
        rounds = floor(51f / names.size).toInt()
        players = names.map{ name ->
            Player(
                name = name,
                bids = MutableList(rounds){0},
                wins = MutableList(rounds){0},
                scores = MutableList(rounds){0}
                )
        }.toMutableList()

        startGame()
    }

    suspend fun restoreGame(sharedPref: SharedPreferences, db: PlayerDatabase) {
        val gameOngoing = sharedPref.getBoolean("game_ongoing", false)
        if (!gameOngoing) {
            gameProcess = GAMEPROCESS.INIT
            return
        }

        val playerCount = sharedPref.getInt("player_count", 0)
        val restoreRound = sharedPref.getInt("current_round", 0)
        val gamePlaying = sharedPref.getBoolean("game_playing", false)
        val gameEnded = sharedPref.getBoolean("game_ended", false)

        players.clear()

        rounds = floor(51f / playerCount).toInt()

        try {
            val playerDao = db.playerRoundDao()
            val playerList = playerDao.getPlayers()

            for (i in 0 until playerCount) {
                val playerName = playerList[i].name
                val bids = MutableList(rounds){0}
                val wins = MutableList(rounds){0}
                val scores = MutableList(rounds){0}

                for(roundIndex in 0 until rounds){
                    val round = playerDao.getPlayerRound(i, roundIndex).firstOrNull()?: continue
                    bids[roundIndex] = round.bid
                    wins[roundIndex] = round.win
                    scores[roundIndex] = round.score
                }

                val player = Player(
                    name = playerName,
                    bids = bids,
                    wins = wins,
                    scores = scores
                )

                players.add(player)
            }

            currentRound = restoreRound

            gameProcess = when {
                gamePlaying -> GAMEPROCESS.PLAYING
                gameEnded -> GAMEPROCESS.ENDED
                else -> GAMEPROCESS.BIDDING
            }

            playerDao.clearPlayerTable()
            playerDao.clearRoundTable()
        } catch (e: Exception) {
            newGame()
        }
    }

    suspend fun saveGame(sharedPref: SharedPreferences, db: PlayerDatabase) {
        if (gameProcess == GAMEPROCESS.INIT) {
            sharedPref.edit {
                putBoolean("game_ongoing", false)
            }
            return
        }

        sharedPref.edit{
            putInt("player_count", playerCount)
            putInt("current_round", currentRound)
            putBoolean("game_ongoing", true)
            when (gameProcess) {
                GAMEPROCESS.BIDDING -> {
                    putBoolean("game_playing", false)
                    putBoolean("game_ended", false)
                }

                GAMEPROCESS.PLAYING -> {
                    putBoolean("game_playing", true)
                    putBoolean("game_ended", false)
                }

                GAMEPROCESS.ENDED -> {
                    putBoolean("game_playing", false)
                    putBoolean("game_ended", true)
                }

                else -> {}
            }
        }

        val playerDao = db.playerRoundDao()
        playerDao.clearPlayerTable()
        playerDao.clearRoundTable()

        for ((i, player) in players.withIndex()) {
            val playerDB = PlayerDB(
                pid = i,
                name = player.name,
                total = player.total
                )
            playerDao.addPlayer(playerDB)

            for (j in 0 until rounds){
                val round = Round(
                    round = j,
                    pid = i,
                    bid = player.bids.getOrNull(j) ?: 0,
                    win = player.wins.getOrNull(j) ?: 0,
                    score = player.scores.getOrNull(j) ?: 0
                )
                playerDao.addPlayerRound(round)
            }
        }
    }

    fun saveGameToDb(sharedPref: SharedPreferences, db: PlayerDatabase){
        viewModelScope.launch(Dispatchers.IO){
            saveGame(sharedPref, db)
        }
    }

    fun restoreGameFromDb(sharedPref: SharedPreferences, db: PlayerDatabase){
        viewModelScope.launch(Dispatchers.IO){
            restoreGame(sharedPref, db)
        }
    }

    fun startGame(){
        gameProcess = GAMEPROCESS.BIDDING
        currentRound = 1
    }

    fun newGame(){
        gameProcess = GAMEPROCESS.INIT
        players.clear()
        currentRound = 0
    }

    fun endGame(){
        gameProcess = GAMEPROCESS.ENDED
    }

    fun bidding(){
        gameProcess = GAMEPROCESS.BIDDING
    }

    fun playing(){
        gameProcess = GAMEPROCESS.PLAYING
    }

    fun nextRound(){
        currentRound = (currentRound) + 1
        bidding()
    }

    fun saveBids(bids: List<Int>){
        for((i, bid) in bids.withIndex()){
            players[i].bids[currentRound - 1] = bid
        }
    }

    fun saveWins(wins: List<Int>){
        for((i, win) in wins.withIndex()){
            players[i].wins[currentRound - 1] = win
        }
    }

    fun calcRoundScores(){
        for(player in players){
            if(player.bids[currentRound - 1] == player.wins[currentRound - 1]) {
                player.scores[currentRound - 1] = 10 + player.bids[currentRound - 1] * player.bids[currentRound - 1]
            }
            else{
                val diff = player.bids[currentRound - 1] - player.wins[currentRound - 1]
                player.scores[currentRound - 1] = -(diff * diff)
            }
        }
    }
}