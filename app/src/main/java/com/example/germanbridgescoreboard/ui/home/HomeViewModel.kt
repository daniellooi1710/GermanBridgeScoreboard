package com.example.germanbridgescoreboard.ui.home

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.germanbridgescoreboard.Game
import com.example.germanbridgescoreboard.R

class HomeViewModel : ViewModel() {
    var playerNum = MutableLiveData<Int>(2)

    fun add(){
        playerNum.value = playerNum.value?.plus(1)
    }

    fun minus(){
        playerNum.value = playerNum.value?.minus(1)
    }
}