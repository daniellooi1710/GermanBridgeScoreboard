package com.example.germanbridgescoreboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.pow

class MainViewModel : ViewModel() {
    /*companion object{

    }
    lateinit var game : Game*/

    var gameStarted = MutableLiveData<Boolean>(false)

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
        bid = Array(playerCount){0}
        win = Array(playerCount){0}
        score = Array(playerCount){0}
        total = Array(playerCount){0}

        playerBids = Array(playerCount){bid}
        playerWins = Array(playerCount){win}
        playerScores = Array(playerCount){score}
        playerTotals = Array(playerCount){total}
    }

    fun startGame(){
        gameStarted.value = true
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

    fun checkBids(): Boolean{
        var bids : Int = 0
        for(i in 0..<playerCount){
            bids += playerBids[i][currentRound.value!! - 1]
        }
        if (bids == currentRound.value) return false
        return true
    }

    fun calcScores(){
        val currentRoundIndex = currentRound.value!! - 1
        for(i in 0..<playerCount){
            if(playerBids[i][currentRoundIndex] == playerWins[i][currentRoundIndex]){
                playerScores[i][currentRoundIndex] = 10 + playerBids[i][currentRoundIndex].toFloat().pow(2).toInt()
            }
            else{
                playerScores[i][currentRoundIndex] = (playerWins[i][currentRoundIndex] - playerBids[i][currentRoundIndex]).absoluteValue
            }
        }
    }

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }

}