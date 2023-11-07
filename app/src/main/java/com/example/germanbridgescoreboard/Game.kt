package com.example.germanbridgescoreboard

import kotlin.math.floor

class Game (var playerCount: Int){
    var rounds: Int = if(52 % playerCount != 0) floor(52.0/playerCount).toInt() else (52/playerCount - 1)
    var currentRound: Int = 0
    var players = Array(playerCount){Player(rounds)}
}