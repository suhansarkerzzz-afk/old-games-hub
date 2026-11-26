package com.oldgameshub.app.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GameLibraryRepositoryTest {
    private lateinit var database: HubDatabase
    private lateinit var repository: GameLibraryRepository

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, HubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = GameLibraryRepository(database.gameLibraryStateDao())
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun favoriteCanBeAddedAndRemoved() = runBlocking {
        repository.setFavorite("snake-classic", true)

        val savedFavorites = repository.favorites.first()
        assertEquals(1, savedFavorites.size)
        assertEquals("snake-classic", savedFavorites.first().gameId)
        assertTrue(savedFavorites.first().isFavorite)

        repository.setFavorite("snake-classic", false)

        assertTrue(repository.favorites.first().isEmpty())
        assertFalse(database.gameLibraryStateDao().findByGameId("snake-classic")!!.isFavorite)
    }

    @Test
    fun playHistoryIncrementsAndKeepsFavoriteState() = runBlocking {
        repository.setFavorite("brick-breaker", true)
        repository.recordPlayed("brick-breaker")
        repository.recordPlayed("brick-breaker")

        val saved = database.gameLibraryStateDao().findByGameId("brick-breaker")!!
        assertTrue(saved.isFavorite)
        assertEquals(2, saved.playCount)
        assertTrue(saved.lastPlayedAt != null)
        assertEquals("brick-breaker", repository.recentlyPlayed.first().first().gameId)
    }
}
