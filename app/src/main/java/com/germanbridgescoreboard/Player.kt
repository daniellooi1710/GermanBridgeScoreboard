package com.germanbridgescoreboard

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.RoomDatabase

@Entity
data class Player (
    @PrimaryKey val pid: Int,
    val name: String,
    val total: Int
)

@Entity(foreignKeys = [ForeignKey(
    entity = Player::class,
    parentColumns = ["pid"],
    childColumns = ["pid"],
    onDelete = ForeignKey.CASCADE
)], primaryKeys = ["round", "pid"])
data class Round (
    val round: Int,
    val pid: Int,
    val bid: Int,
    val win: Int,
    val score: Int
)

data class PlayerRound(
    @Embedded val pid: Int,
    @Relation(
        parentColumn = "pid",
        entityColumn = "round"
    )
    val rounds: List<Round>
)

@Database(entities = [Player::class, Round::class], version = 1, exportSchema = true)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerRoundDao(): PlayerRoundDao
}

@Dao
interface PlayerRoundDao {
    @Query("SELECT * FROM Player")
    fun getPlayers(): List<Player>

    @Query("SELECT * FROM Round WHERE (pid = :pid AND round = :round)")
    fun getPlayerRound(pid: Int, round: Int): List<Round>

    @Query("DELETE FROM Player")
    fun clearPlayerTable()

    @Query("DELETE FROM Round")
    fun clearRoundTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlayer(player: Player)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlayerRound(round: Round)
}
