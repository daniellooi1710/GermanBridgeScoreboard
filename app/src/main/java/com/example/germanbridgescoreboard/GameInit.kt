package com.example.germanbridgescoreboard

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class GameInit(numPlayers: Int) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val rvPlayers = findViewById<View>(R.id.input_player_name_field) as RecyclerView
    }
}