package com.example.germanbridgescoreboard.ui.home

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
    companion object {
        var game = MutableLiveData<Game>()
    }

    var playerNum : MutableLiveData<Int> = init(gameStarted.value!!)

    fun init(start: Boolean): MutableLiveData<Int>{
        if(start) return playerNum
        else return MutableLiveData<Int>(2)
    }

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }

    fun createGame(){
        game.value = playerNum.value?.let { Game(it) }!!
    }

    fun newGame(){
        gameStarted.value = false
    }

}