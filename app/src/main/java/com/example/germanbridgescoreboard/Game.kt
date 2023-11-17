package com.example.germanbridgescoreboard

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Game (
    @PrimaryKey val gameid: Int,
    @ColumnInfo(name = "playercount") var playerCount: Int,
    @ColumnInfo(name = "rounds") var rounds: Int,
    @ColumnInfo(name = "currentround") var currentRound: Int,
)