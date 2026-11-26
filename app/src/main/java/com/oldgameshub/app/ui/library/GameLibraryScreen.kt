package com.oldgameshub.app.ui.library

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oldgameshub.app.data.GameAccent
import com.oldgameshub.app.data.GameCatalog
import com.oldgameshub.app.data.RetroGame
import com.oldgameshub.app.ui.theme.Coral
import com.oldgameshub.app.ui.theme.DeepNavy
import com.oldgameshub.app.ui.theme.RoyalBlue
import com.oldgameshub.app.ui.theme.Slate
import com.oldgameshub.app.ui.theme.SoftBlue
import com.oldgameshub.app.ui.theme.WarmIvory

@Composable
fun GameLibraryScreen(
    initialQuery: String,
    onGameClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by rememberSaveable { mutableStateOf(initialQuery) }
    var selectedCategory by rememberSaveable { mutableStateOf("All") }
    var selectedSort by rememberSaveable { mutableStateOf("Popular") }
    var sortMenuExpanded by remember { mutableStateOf(false) }

    val filteredGames = remember(searchQuery, selectedCategory, selectedSort) {
        val matchingGames = GameCatalog.games.filter { game ->
            val matchesQuery = searchQuery.isBlank() ||
                game.title.contains(searchQuery, ignoreCase = true) ||
                game.category.contains(searchQuery, ignoreCase = true) ||
                game.tagline.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == "All" || game.category == selectedCategory
            matchesQuery && matchesCategory
        }
        when (selectedSort) {
            "Name A–Z" -> matchingGames.sortedBy { it.title }
            "Name Z–A" -> matchingGames.sortedByDescending { it.title }
            else -> matchingGames.sortedBy { it.popularityRank }
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Game Library",
                    color = DeepNavy,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "তোমার পছন্দের Classic গেম খুঁজে নাও",
                    color = Slate,
                    fontSize = 14.sp
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholder = { Text("গেমের নাম লিখে খুঁজুন") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp)
            )
        }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Categories", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(GameCatalog.categories) { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(if (category == "All") "সব গেম" else category) }
                        )
                    }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredGames.size} টি গেম",
                    color = Slate,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                Box {
                    TextButton(onClick = { sortMenuExpanded = true }) {
                        Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(17.dp))
                        Spacer(Modifier.width(5.dp))
                        Text(selectedSort)
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = sortMenuExpanded,
                        onDismissRequest = { sortMenuExpanded = false }
                    ) {
                        listOf("Popular", "Name A–Z", "Name Z–A").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedSort = option
                                    sortMenuExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        if (filteredGames.isEmpty()) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Slate, modifier = Modifier.size(32.dp))
                        Text("কোনো গেম পাওয়া যায়নি", color = DeepNavy, fontWeight = FontWeight.Bold)
                        Text("অন্য নাম বা Category দিয়ে খুঁজে দেখো।", color = Slate, fontSize = 13.sp)
                    }
                }
            }
        } else {
            items(filteredGames, key = { it.id }) { game ->
                GameLibraryCard(game = game, onClick = { onGameClick(game.id) })
            }
        }

        item {
            Surface(
                color = SoftBlue,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "নতুন গেম ধাপে ধাপে যোগ হবে। এই তালিকার গেমগুলো এখনো playable নয়।",
                    color = DeepNavy,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
private fun GameLibraryCard(game: RetroGame, onClick: () -> Unit) {
    val tileColor = when (game.accent) {
        GameAccent.BLUE -> SoftBlue
        GameAccent.CORAL -> Color(0xFFF8E9E4)
        GameAccent.NAVY -> Color(0xFFE7EAF0)
        GameAccent.CREAM -> Color(0xFFF2EBDD)
    }
    val symbolColor = if (game.accent == GameAccent.CORAL) Coral else RoyalBlue

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(66.dp)
                    .background(tileColor, RoundedCornerShape(17.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.symbol,
                    color = symbolColor,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(game.title, color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(game.tagline, color = Slate, fontSize = 12.sp, maxLines = 2)
                Surface(color = SoftBlue, shape = CircleShape) {
                    Text(
                        text = game.category,
                        color = RoyalBlue,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                    )
                }
            }
            Text("›", color = RoyalBlue, fontSize = 27.sp, fontWeight = FontWeight.Medium)
        }
    }
}
