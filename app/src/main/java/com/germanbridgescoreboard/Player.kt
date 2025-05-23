package com.germanbridgescoreboard

import android.content.Context
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
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity
data class PlayerDB (
    @PrimaryKey val pid: Int,
    val name: String,
    val total: Int
)

@Entity(foreignKeys = [ForeignKey(
    entity = PlayerDB::class,
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
    @Embedded val player: PlayerDB,
    @Relation(
        parentColumn = "pid",
        entityColumn = "pid"
    )
    val rounds: List<Round>
)

@Database(entities = [PlayerDB::class, Round::class], version = 1, exportSchema = true)
abstract class PlayerDatabase : RoomDatabase() {
    abstract fun playerRoundDao(): PlayerRoundDao
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: PlayerDatabase? = null

    fun getDatabase(context: Context): PlayerDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlayerDatabase::class.java,
                        "player_database"
                    ).fallbackToDestructiveMigration(false)
                .build()
            INSTANCE = instance
            instance
        }
    }
}


@Dao
interface PlayerRoundDao {
    @Query("SELECT * FROM PlayerDB")
    suspend fun getPlayers(): List<PlayerDB>

    @Query("SELECT * FROM Round WHERE (pid = :pid AND round = :round)")
    suspend fun getPlayerRound(pid: Int, round: Int): List<Round>

    @Query("DELETE FROM PlayerDB")
    suspend fun clearPlayerTable()

    @Query("DELETE FROM Round")
    suspend fun clearRoundTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayer(player: PlayerDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayerRound(round: Round)
}
