package com.oldgameshub.app.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data class LibraryRoute(val query: String = "")

@Serializable
data object MyGamesRoute

@Serializable
data object ProfileRoute

@Serializable
data class GameDetailsRoute(val gameId: String)
