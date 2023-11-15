package com.example.germanbridgescoreboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.pow

class MainViewModel : ViewModel() {
    enum class GAMEPROCESS{
        BIDDING,
        PLAYING
    }

    var gameStarted = MutableLiveData<Boolean>(false)
    var gameProcess = MutableLiveData<GAMEPROCESS>(GAMEPROCESS.BIDDING)

    var playerCount: Int = 0
    var rounds: Int = 0
    var currentRound = MutableLiveData<Int>(0)

    var playerNum : MutableLiveData<Int> = if(playerCount == 0) MutableLiveData<Int>(2) else MutableLiveData<Int>(playerCount)

    var name: String = ""
    lateinit var bid : Array<Int>
    lateinit var win : Array<Int>
    lateinit var score : Array<Int>
    lateinit var total: Array<Int>

    lateinit var players : Array<String>
    lateinit var playerBids : Array<Array<Int>>
    lateinit var playerWins : Array<Array<Int>>
    lateinit var playerScores : Array<Array<Int>>
    lateinit var playerTotals : Array<Array<Int>>

    fun initGame(){
        playerCount = playerNum.value!!
        rounds = if(52 % playerCount != 0) floor(52.0/playerCount).toInt() else (52/playerCount - 1)
        players = Array(playerCount){name}
        bid = Array(rounds){0}
        win = Array(rounds){0}
        score = Array(rounds){0}
        total = Array(playerCount){0}

        playerBids = Array(playerCount){Array(rounds){0}}
        playerWins = Array(playerCount){Array(rounds){0}}
        playerScores = Array(playerCount){Array(rounds){0}}
    }

    fun startGame(){
        gameStarted.value = true
        gameProcess.value = GAMEPROCESS.BIDDING
        currentRound.value = 1
    }

    fun newGame(){
        gameStarted.value = false
        playerCount = 0
        currentRound.value = 0
    }

    fun endGame(){
        gameStarted.value = false
    }

    fun calcScores(){
        val currentRoundIndex = currentRound.value!! - 1
        for(i in 0 until playerCount){
            Log.d("player", players[i])
            Log.d("player", playerBids[i][currentRoundIndex].toString())
            Log.d("player", playerWins[i][currentRoundIndex].toString())
            if(playerBids[i][currentRoundIndex] == playerWins[i][currentRoundIndex]){
                playerScores[i][currentRoundIndex] = 10 + playerBids[i][currentRoundIndex].toFloat().pow(2).toInt()
            }
            else{
                playerScores[i][currentRoundIndex] = -((playerWins[i][currentRoundIndex] - playerBids[i][currentRoundIndex]).absoluteValue)
            }
            total[i] = playerScores[i].sum()
        }
    }

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }

    fun nextRound(){
        currentRound.value = currentRound.value?.plus(1)
    }

    fun bidding(){
        gameProcess.value = GAMEPROCESS.BIDDING
    }

    fun playing(){
        gameProcess.value = GAMEPROCESS.PLAYING
    }

}