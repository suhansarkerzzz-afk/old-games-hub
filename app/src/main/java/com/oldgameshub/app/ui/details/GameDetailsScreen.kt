package com.oldgameshub.app.ui.details

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.oldgameshub.app.data.GameAccent
import com.oldgameshub.app.data.RetroGame
import com.oldgameshub.app.ui.theme.Coral
import com.oldgameshub.app.ui.theme.DeepNavy
import com.oldgameshub.app.ui.theme.RoyalBlue
import com.oldgameshub.app.ui.theme.Slate
import com.oldgameshub.app.ui.theme.SoftBlue

@Composable
fun GameDetailsScreen(
    game: RetroGame?,
    isFavorite: Boolean,
    onFavoriteChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showComingSoonMessage by rememberSaveable { mutableStateOf(false) }

    if (game == null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DeepNavy)
            }
            Text("গেমটি পাওয়া যায়নি", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text("Library থেকে অন্য একটি গেম নির্বাচন করো।", color = Slate)
        }
    } else {
        val tileColor = when (game.accent) {
            GameAccent.BLUE -> SoftBlue
            GameAccent.CORAL -> Color(0xFFF8E9E4)
            GameAccent.NAVY -> Color(0xFFE7EAF0)
            GameAccent.CREAM -> Color(0xFFF2EBDD)
        }
        val symbolColor = if (game.accent == GameAccent.CORAL) Coral else RoyalBlue

        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(17.dp)
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DeepNavy)
                    }
                    Text("Game Details", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                    IconButton(onClick = { onFavoriteChange(!isFavorite) }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = if (isFavorite) Coral else RoyalBlue
                        )
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = tileColor)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(22.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(112.dp)
                                .background(Color.White.copy(alpha = 0.72f), RoundedCornerShape(28.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                game.symbol,
                                color = symbolColor,
                                fontSize = 52.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Surface(color = Color.White, shape = RoundedCornerShape(50.dp)) {
                            Text(
                                "PLANNED GAME",
                                color = RoyalBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                        Text(
                            game.title,
                            color = DeepNavy,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 25.sp
                        )
                        Text(game.tagline, color = Slate, fontSize = 13.sp)
                    }
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("About this game", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(game.description, color = Slate, fontSize = 14.sp, lineHeight = 21.sp)
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    DetailInfoCard("CATEGORY", game.category, Modifier.weight(1f))
                    DetailInfoCard("PLAYERS", game.players, Modifier.weight(1f))
                    DetailInfoCard("DIFFICULTY", game.difficulty, Modifier.weight(1f))
                }
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Controls", color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    game.controls.forEach { control ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("•", color = Coral, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(Modifier.width(8.dp))
                            Text(control, color = Slate, fontSize = 14.sp, lineHeight = 20.sp)
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { showComingSoonMessage = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Play — Coming Soon", fontWeight = FontWeight.Bold)
                }
            }

            if (showComingSoonMessage) {
                item {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = SoftBlue,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            "এই গেমের playable version এখনো তৈরি হয়নি। Gameplay Engine পরের ধাপে যোগ হবে।",
                            color = DeepNavy,
                            fontSize = 13.sp,
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    "এই পেজটি গেমের পরিকল্পনা ও তথ্য দেখায়। আসল Gameplay Engine পরে যোগ করা হবে।",
                    color = Slate,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun DetailInfoCard(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(label, color = Slate, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Text(value, color = DeepNavy, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
