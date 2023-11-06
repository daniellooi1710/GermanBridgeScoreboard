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
    var gameStarted = false
    companion object {
        lateinit var game: Game
    }

    var playerNum = MutableLiveData<Int>(2)

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }

    fun createGame(){
        game = playerNum.value?.let { Game(it) }!!
        gameStarted = true
    }

    fun newGame(){
        gameStarted = false
    }


}