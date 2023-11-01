package com.example.germanbridgescoreboard

import kotlin.math.floor

class Game (var playerCount: Int){
    private var playerName: ArrayList<String> = arrayListOf()
    private var players: ArrayList<Player> = arrayListOf()
    var rounds: Int = floor(52.0/playerCount).toInt()

    fun addPlayer(name: String){
        playerName.add(name)
        players.add(Player(name))
    }


}