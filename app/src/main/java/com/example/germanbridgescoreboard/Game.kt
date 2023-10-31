package com.example.germanbridgescoreboard

class Game (var playerCount: Int){
    var playerName: ArrayList<String> = arrayListOf()
    var players: ArrayList<Player> = arrayListOf()
    var rounds: Int = Math.floor(52.0/playerCount).toInt()
}