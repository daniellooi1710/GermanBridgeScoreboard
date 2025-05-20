package com.germanbridgescoreboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.floor
import kotlin.math.pow

class MainViewModel : ViewModel() {
    enum class GAMEPROCESS{
        INIT,
        BIDDING,
        PLAYING,
        ENDED
    }

    var gameProcess = MutableLiveData(GAMEPROCESS.BIDDING)

    var playerCount: Int = 0
    var rounds: Int = 0
    var currentRound = MutableLiveData(0)

    var playerNum : MutableLiveData<Int> = MutableLiveData<Int>(2)

    // TODO: Implement class for Player
    inner class Player(val name: String){
        var bids : Array<Int> = Array(rounds){0}
        var wins : Array<Int> = Array(rounds){0}
        var scores : Array<Int> = Array(rounds){0}
    }

    var name: String = ""
    lateinit var total: Array<Int>

    lateinit var players : Array<String>
    // playerBids[i][r]: bids of player i in round r
    lateinit var playerBids : Array<Array<Int>>
    // playerWins[i][r]: wins of player i in round r
    lateinit var playerWins : Array<Array<Int>>
    // score matrix transposed for natural looping over rounds
    // playerScoresT[r][i]: score of player i in round r
    // we access the scores within each round rather than each player, efficient and intuitive
    lateinit var playerScoresT : Array<Array<Int>>

    /**
     * Initialize the game.
     *
     * Inserts all names of players, calculate number of rounds, and initializes score arrays.
     */
    fun initGame(){
        playerCount = playerNum.value!!
        rounds = if(52 % playerCount != 0) floor(52.0/playerCount).toInt() else (52/playerCount - 1)
        players = Array(playerCount){name}
        total = Array(playerCount){0}

        playerBids = Array(playerCount){Array(rounds){0}}
        playerWins = Array(playerCount){Array(rounds){0}}
        playerScoresT = Array(rounds){Array(playerCount){0}}
    }

    fun startGame(){
        gameProcess.value = GAMEPROCESS.BIDDING
        currentRound.value = 1
    }

    fun newGame(){
        gameProcess.value = GAMEPROCESS.INIT
        playerCount = 0
        currentRound.value = 0
    }

    fun endGame(){
        gameProcess.value = GAMEPROCESS.ENDED
    }

    fun calcScores(){
        val currentRoundIndex = currentRound.value!! - 1
        for(i in 0 until playerCount){
            if(playerBids[i][currentRoundIndex] == playerWins[i][currentRoundIndex]){
                playerScoresT[currentRoundIndex][i] = 10 + playerBids[i][currentRoundIndex].toFloat().pow(2).toInt()
            }
            else{
                playerScoresT[currentRoundIndex][i] = -((playerWins[i][currentRoundIndex] - playerBids[i][currentRoundIndex]).toFloat().pow(2).toInt())
            }
            total[i] += playerScoresT[currentRoundIndex][i]
        }
    }

    fun add(){
        playerNum.postValue((playerNum.value ?: 2) + 1)
    }

    fun minus(){
        playerNum.postValue((playerNum.value ?: 2) - 1)
    }

    fun nextRound(){
        currentRound.postValue((currentRound.value ?: 0) + 1)
    }

    fun bidding(){
        gameProcess.value = GAMEPROCESS.BIDDING
    }

    fun playing(){
        gameProcess.value = GAMEPROCESS.PLAYING
    }

}