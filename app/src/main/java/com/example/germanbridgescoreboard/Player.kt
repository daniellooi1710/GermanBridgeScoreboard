package com.example.germanbridgescoreboard

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class Player (
    @PrimaryKey val pid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "bids") val bids: Array<Int>,
    @ColumnInfo(name = "wins") val wins: Array<Int>,
    @ColumnInfo(name = "scores") val score: Array<Int>,
    @ColumnInfo(name = "total") val total: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (pid != other.pid) return false
        if (name != other.name) return false
        if (!bids.contentEquals(other.bids)) return false
        if (!wins.contentEquals(other.wins)) return false
        if (!score.contentEquals(other.score)) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pid
        result = 420769 * result + name.hashCode()
        result = 420769 * result + bids.contentHashCode()
        result = 420769 * result + wins.contentHashCode()
        result = 420769 * result + score.contentHashCode()
        result = 420769 * result + total
        return result
    }
}

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getPlayers(): List<Player>
}