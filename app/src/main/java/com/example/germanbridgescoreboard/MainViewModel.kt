package com.example.germanbridgescoreboard

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

    var gameStarted = MutableLiveData(false)
    var gameProcess = MutableLiveData(GAMEPROCESS.BIDDING)

    var playerCount: Int = 0
    var rounds: Int = 0
    var currentRound = MutableLiveData(0)

    var playerNum : MutableLiveData<Int> = if(playerCount == 0) MutableLiveData<Int>(2) else MutableLiveData<Int>(playerCount)

    var name: String = ""
    lateinit var total: Array<Int>

    lateinit var players : Array<String>
    lateinit var playerBids : Array<Array<Int>>
    lateinit var playerWins : Array<Array<Int>>
    lateinit var playerScores : Array<Array<Int>>
    lateinit var playerScoresT : Array<Array<Int>>

    fun initGame(){
        playerCount = playerNum.value!!
        rounds = if(52 % playerCount != 0) floor(52.0/playerCount).toInt() else (52/playerCount - 1)
        players = Array(playerCount){name}
        total = Array(playerCount){0}

        playerBids = Array(playerCount){Array(rounds){0}}
        playerWins = Array(playerCount){Array(rounds){0}}
        playerScores = Array(playerCount){Array(rounds){0}}
        playerScoresT = Array(rounds){Array(playerCount){0}}
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
            if(playerBids[i][currentRoundIndex] == playerWins[i][currentRoundIndex]){
                playerScoresT[currentRoundIndex][i] = 10 + playerBids[i][currentRoundIndex].toFloat().pow(2).toInt()
            }
            else{
                playerScoresT[currentRoundIndex][i] = -((playerWins[i][currentRoundIndex] - playerBids[i][currentRoundIndex]).absoluteValue)
            }
            total[i] += playerScoresT[currentRoundIndex][i]
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