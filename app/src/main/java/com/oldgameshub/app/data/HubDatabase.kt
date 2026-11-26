package com.oldgameshub.app.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "game_library_state")
data class GameLibraryStateEntity(
    @PrimaryKey val gameId: String,
    val isFavorite: Boolean = false,
    val favoriteUpdatedAt: Long = 0L,
    val lastPlayedAt: Long? = null,
    val playCount: Int = 0,
    val isDownloaded: Boolean = false
)

@Dao
interface GameLibraryStateDao {
    @Query("SELECT * FROM game_library_state WHERE isFavorite = 1 ORDER BY favoriteUpdatedAt DESC")
    fun observeFavorites(): Flow<List<GameLibraryStateEntity>>

    @Query("SELECT * FROM game_library_state WHERE lastPlayedAt IS NOT NULL ORDER BY lastPlayedAt DESC")
    fun observeRecentlyPlayed(): Flow<List<GameLibraryStateEntity>>

    @Query("SELECT * FROM game_library_state WHERE gameId = :gameId LIMIT 1")
    suspend fun findByGameId(gameId: String): GameLibraryStateEntity?

    @Upsert
    suspend fun upsert(state: GameLibraryStateEntity)
}

@Database(
    entities = [GameLibraryStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HubDatabase : RoomDatabase() {
    abstract fun gameLibraryStateDao(): GameLibraryStateDao

    companion object {
        @Volatile
        private var INSTANCE: HubDatabase? = null

        fun getInstance(context: Context): HubDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    HubDatabase::class.java,
                    "old_games_hub.db"
                ).build().also { INSTANCE = it }
            }
    }
}

class GameLibraryRepository(
    private val dao: GameLibraryStateDao
) {
    val favorites: Flow<List<GameLibraryStateEntity>> = dao.observeFavorites()
    val recentlyPlayed: Flow<List<GameLibraryStateEntity>> = dao.observeRecentlyPlayed()

    suspend fun setFavorite(gameId: String, isFavorite: Boolean) {
        val current = dao.findByGameId(gameId) ?: GameLibraryStateEntity(gameId = gameId)
        dao.upsert(
            current.copy(
                isFavorite = isFavorite,
                favoriteUpdatedAt = if (isFavorite) System.currentTimeMillis() else 0L
            )
        )
    }

    suspend fun recordPlayed(gameId: String) {
        val current = dao.findByGameId(gameId) ?: GameLibraryStateEntity(gameId = gameId)
        dao.upsert(
            current.copy(
                lastPlayedAt = System.currentTimeMillis(),
                playCount = current.playCount + 1
            )
        )
    }

    suspend fun setDownloaded(gameId: String, isDownloaded: Boolean) {
        val current = dao.findByGameId(gameId) ?: GameLibraryStateEntity(gameId = gameId)
        dao.upsert(current.copy(isDownloaded = isDownloaded))
    }
}
