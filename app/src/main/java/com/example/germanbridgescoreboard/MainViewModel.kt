package com.example.germanbridgescoreboard

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.MainActivity
import com.example.germanbridgescoreboard.R

class MainViewModel : ViewModel() {
    var gameStarted = MutableLiveData<Boolean>(false)

    companion object{

    }
    lateinit var game : Game

    var playerNum : MutableLiveData<Int> = MutableLiveData<Int>(2)

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }

    fun createGame(){
        val num: Int = playerNum.value!!
        game = Game(num)
    }

    fun startGame(){
        gameStarted.value = true
    }

    fun newGame(){
        gameStarted.value = false
    }

}