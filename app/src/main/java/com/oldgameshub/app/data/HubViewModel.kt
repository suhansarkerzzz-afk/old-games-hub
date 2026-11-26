package com.oldgameshub.app.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HubViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameLibraryRepository(
        HubDatabase.getInstance(application).gameLibraryStateDao()
    )

    val favorites = repository.favorites.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    val recentlyPlayed = repository.recentlyPlayed.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun setFavorite(gameId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(gameId, isFavorite)
        }
    }

    fun recordPlayed(gameId: String) {
        viewModelScope.launch {
            repository.recordPlayed(gameId)
        }
    }

    fun setDownloaded(gameId: String, isDownloaded: Boolean) {
        viewModelScope.launch {
            repository.setDownloaded(gameId, isDownloaded)
        }
    }
}
