package com.oldgameshub.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oldgameshub.app.ui.theme.Coral
import com.oldgameshub.app.ui.theme.DeepNavy
import com.oldgameshub.app.ui.theme.OldGamesHubTheme
import com.oldgameshub.app.ui.theme.RoyalBlue
import com.oldgameshub.app.ui.theme.Slate
import com.oldgameshub.app.ui.theme.SoftBlue
import com.oldgameshub.app.ui.theme.SoftCoral
import com.oldgameshub.app.ui.theme.WarmIvory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OldGamesHubTheme {
                OldGamesHubApp()
            }
        }
    }
}

private enum class HubTab(val label: String) {
    Home("Home"),
    Library("Library"),
    MyGames("My Games"),
    Profile("Profile")
}

@Composable
private fun OldGamesHubApp() {
    var selectedTab by rememberSaveable { mutableStateOf(HubTab.Home) }

    Scaffold(
        containerColor = WarmIvory,
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                HubTab.entries.forEach { tab ->
                    val icon = when (tab) {
                        HubTab.Home -> Icons.Default.Home
                        HubTab.Library -> Icons.Default.Search
                        HubTab.MyGames -> Icons.Default.Favorite
                        HubTab.Profile -> Icons.Default.Person
                    }
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { Icon(icon, contentDescription = tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            HubTab.Home -> HomeScreen(Modifier.padding(innerPadding))
            HubTab.Library -> SimpleSection(
                title = "Game Library",
                subtitle = "সব Classic গেম এক জায়গায়",
                modifier = Modifier.padding(innerPadding)
            )
            HubTab.MyGames -> SimpleSection(
                title = "My Games",
                subtitle = "তোমার পছন্দের ও সম্প্রতি খেলা গেম",
                modifier = Modifier.padding(innerPadding)
            )
            HubTab.Profile -> SimpleSection(
                title = "Player Profile",
                subtitle = "তোমার গেমিং যাত্রা এখানে",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun HomeScreen(modifier: Modifier = Modifier) {
    var searchText by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 22.dp, bottom = 28.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(RoyalBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(27.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("OLD GAMES HUB", fontWeight = FontWeight.ExtraBold, color = DeepNavy, letterSpacing = 1.sp)
                    Text("পুরোনো দিনের গেম, নতুন আনন্দ", color = Slate, fontSize = 12.sp)
                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SoftCoral),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = "Favorites", tint = Coral)
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("আবার খেলায় ফিরে চলো!", style = MaterialTheme.typography.headlineSmall, color = DeepNavy, fontWeight = FontWeight.Bold)
                Text("তোমার প্রিয় Classic গেমগুলো খুঁজে নাও।", color = Slate)
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = { Text("গেম খুঁজুন / Search games") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = RoyalBlue)
            ) {
                Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("RETRO COLLECTION", color = SoftBlue, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
                    Text("The classics
never get old.", color = MaterialTheme.colorScheme.onPrimary, fontSize = 27.sp, lineHeight = 32.sp, fontWeight = FontWeight.ExtraBold)
                    Text("এক জায়গায় ফিরে পাও পুরোনো গেমের মজা।", color = SoftBlue)
                    TextButton(onClick = { }) {
                        Text("Explore Games  →", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item {
            SectionHeading("Continue Playing", "চালিয়ে যাও")
            GameRow("Snake Classic", "Arcade • Classic", "↗", SoftBlue)
        }
        item {
            SectionHeading("Popular Classics", "জনপ্রিয় গেম")
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                GameRow("Brick Breaker", "Arcade • Skill", "▦", SoftCoral)
                GameRow("Space Shooter", "Action • Retro", "✦", SoftBlue)
                GameRow("Pong Classic", "Sports • 2 Player", "◉", SoftCoral)
            }
        }
    }
}

@Composable
private fun SectionHeading(title: String, subtitle: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(subtitle, color = Slate, fontSize = 12.sp)
        }
        Text("See all", color = RoyalBlue, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
private fun GameRow(title: String, category: String, symbol: String, tileColor: androidx.compose.ui.graphics.Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(13.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(tileColor),
                contentAlignment = Alignment.Center
            ) {
                Text(symbol, color = RoyalBlue, fontSize = 25.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(13.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = DeepNavy, fontWeight = FontWeight.Bold)
                Text(category, color = Slate, fontSize = 12.sp)
            }
            Text("Play", color = RoyalBlue, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun SimpleSection(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(WarmIvory)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(title, color = DeepNavy, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Slate)
        Text("এই Screen-এর পূর্ণ Feature পরের ধাপে যোগ করা হবে।", color = Slate)
    }
}
