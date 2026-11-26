package com.oldgameshub.app.ui.mygames

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oldgameshub.app.data.GameLibraryStateEntity
import com.oldgameshub.app.data.GameCatalog
import com.oldgameshub.app.data.RetroGame
import com.oldgameshub.app.ui.theme.Coral
import com.oldgameshub.app.ui.theme.DeepNavy
import com.oldgameshub.app.ui.theme.RoyalBlue
import com.oldgameshub.app.ui.theme.Slate
import com.oldgameshub.app.ui.theme.SoftBlue

private enum class MyGamesFilter(val label: String) {
    Favorites("Favorites"),
    Recent("Recently Played"),
    Downloaded("Downloaded")
}

@Composable
fun MyGamesScreen(
    favorites: List<GameLibraryStateEntity>,
    recentlyPlayed: List<GameLibraryStateEntity>,
    onGameClick: (String) -> Unit,
    onFavoriteChange: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by rememberSaveable { mutableStateOf(MyGamesFilter.Favorites.name) }
    val filter = MyGamesFilter.valueOf(selectedFilter)
    val visibleGames = when (filter) {
        MyGamesFilter.Favorites -> favorites.mapNotNull { GameCatalog.findById(it.gameId) }
        MyGamesFilter.Recent -> recentlyPlayed.mapNotNull { GameCatalog.findById(it.gameId) }
        MyGamesFilter.Downloaded -> emptyList()
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 22.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("My Games", color = DeepNavy, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                Text("তোমার নিজের গেম কালেকশন", color = Slate, fontSize = 14.sp)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MyGamesFilter.entries.forEach { option ->
                    FilterChip(
                        selected = filter == option,
                        onClick = { selectedFilter = option.name },
                        label = { Text(option.label) }
                    )
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CollectionStat("Favorites", favorites.size.toString(), Icons.Default.Favorite)
                CollectionStat("Played", recentlyPlayed.size.toString(), Icons.Default.History)
            }
        }
        if (visibleGames.isEmpty()) {
            item {
                EmptyCollectionState(
                    title = when (filter) {
                        MyGamesFilter.Favorites -> "এখনো কোনো Favorite নেই"
                        MyGamesFilter.Recent -> "এখনো কোনো গেম খেলা হয়নি"
                        MyGamesFilter.Downloaded -> "ডাউনলোড করা গেম নেই"
                    },
                    description = when (filter) {
                        MyGamesFilter.Favorites -> "গেমের Details বা Library থেকে Heart চাপলে এখানে পাবে।"
                        MyGamesFilter.Recent -> "গেম খেলার সুবিধা চালু হলে তোমার History এখানে জমা হবে।"
                        MyGamesFilter.Downloaded -> "গেম ডাউনলোড সুবিধা এখনো তৈরি হয়নি।"
                    }
                )
            }
        } else {
            items(visibleGames, key = { it.id }) { game ->
                MyGameRow(
                    game = game,
                    isFavorite = favorites.any { it.gameId == game.id },
                    onClick = { onGameClick(game.id) },
                    onFavoriteClick = { onFavoriteChange(game.id, !favorites.any { it.gameId == game.id }) }
                )
            }
        }
        item {
            Surface(color = SoftBlue, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Text(
                    "গেমগুলো এখনো পরিকল্পিত তালিকা। Playable game যোগ হলে History ও Continue Playing স্বয়ংক্রিয়ভাবে কাজ করবে।",
                    color = DeepNavy,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
private fun CollectionStat(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(color = Color.White, shape = RoundedCornerShape(18.dp), modifier = Modifier.weight(1f)) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(icon, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(22.dp))
            Column {
                Text(value, color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 19.sp)
                Text(label, color = Slate, fontSize = 11.sp)
            }
        }
    }
}

@Composable
private fun EmptyCollectionState(title: String, description: String) {
    Surface(color = Color.White, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Icon(Icons.Default.SportsEsports, contentDescription = null, tint = RoyalBlue, modifier = Modifier.size(38.dp))
            Text(title, color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(description, color = Slate, fontSize = 13.sp, lineHeight = 19.sp)
        }
    }
}

@Composable
private fun MyGameRow(
    game: RetroGame,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(54.dp).background(SoftBlue, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(game.symbol, color = RoyalBlue, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(game.title, color = DeepNavy, fontWeight = FontWeight.Bold)
                Text(game.tagline, color = Slate, fontSize = 11.sp, maxLines = 2)
            }
            androidx.compose.material3.IconButton(onClick = onFavoriteClick) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = if (isFavorite) "Remove favorite" else "Add favorite",
                    tint = if (isFavorite) Coral else Slate
                )
            }
        }
    }
}
