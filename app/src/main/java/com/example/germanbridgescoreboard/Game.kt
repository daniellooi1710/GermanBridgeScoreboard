package com.example.germanbridgescoreboard

import kotlin.math.floor

class Game (var playerCount: Int){
    /*private var playerName: ArrayList<String> = arrayListOf()*/
    private var players: ArrayList<Player> = arrayListOf()
    private var rounds: Int = if(52 % playerCount != 0) floor(52.0/playerCount).toInt() else (52/playerCount - 1)

    fun addPlayer(name: String){
        /*playerName.add(name)*/
        players.add(Player(name))
    }

    fun getRounds() : Int{
        return rounds
    }
}