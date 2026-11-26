package com.oldgameshub.app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oldgameshub.app.data.GameCatalog
import com.oldgameshub.app.navigation.GameDetailsRoute
import com.oldgameshub.app.navigation.HomeRoute
import com.oldgameshub.app.navigation.LibraryRoute
import com.oldgameshub.app.navigation.MyGamesRoute
import com.oldgameshub.app.navigation.ProfileRoute
import com.oldgameshub.app.ui.details.GameDetailsScreen
import com.oldgameshub.app.ui.library.GameLibraryScreen
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
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
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
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val isDetailsScreen = currentDestination?.hasRoute<GameDetailsRoute>() == true

    Scaffold(
        containerColor = WarmIvory,
        bottomBar = {
            if (!isDetailsScreen) {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    HubTab.entries.forEach { tab ->
                        val icon = when (tab) {
                            HubTab.Home -> Icons.Default.Home
                            HubTab.Library -> Icons.Default.Search
                            HubTab.MyGames -> Icons.Default.Favorite
                            HubTab.Profile -> Icons.Default.Person
                        }
                        val selected = when (tab) {
                            HubTab.Home -> currentDestination?.hasRoute<HomeRoute>() == true
                            HubTab.Library -> currentDestination?.hasRoute<LibraryRoute>() == true
                            HubTab.MyGames -> currentDestination?.hasRoute<MyGamesRoute>() == true
                            HubTab.Profile -> currentDestination?.hasRoute<ProfileRoute>() == true
                        }
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                when (tab) {
                                    HubTab.Home -> navController.navigateTopLevel(HomeRoute)
                                    HubTab.Library -> navController.navigateTopLevel(LibraryRoute())
                                    HubTab.MyGames -> navController.navigateTopLevel(MyGamesRoute)
                                    HubTab.Profile -> navController.navigateTopLevel(ProfileRoute)
                                }
                            },
                            icon = { Icon(icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    onOpenLibrary = { query ->
                        navController.navigate(LibraryRoute(query))
                    },
                    onOpenGame = { gameId ->
                        navController.navigate(GameDetailsRoute(gameId))
                    }
                )
            }
            composable<LibraryRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<LibraryRoute>()
                GameLibraryScreen(
                    initialQuery = route.query,
                    onGameClick = { gameId ->
                        navController.navigate(GameDetailsRoute(gameId))
                    }
                )
            }
            composable<GameDetailsRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<GameDetailsRoute>()
                GameDetailsScreen(
                    game = GameCatalog.findById(route.gameId),
                    onBack = { navController.popBackStack() }
                )
            }
            composable<MyGamesRoute> {
                SimpleSection(
                    title = "My Games",
                    subtitle = "তোমার পছন্দের ও সম্প্রতি খেলা গেম"
                )
            }
            composable<ProfileRoute> {
                SimpleSection(
                    title = "Player Profile",
                    subtitle = "তোমার গেমিং যাত্রা এখানে"
                )
            }
        }
    }
}

private inline fun <reified T : Any> NavHostController.navigateTopLevel(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

@Composable
private fun HomeScreen(
    onOpenLibrary: (String) -> Unit,
    onOpenGame: (String) -> Unit
) {
    var searchText by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
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
                    trailingIcon = {
                        IconButton(onClick = { onOpenLibrary(searchText) }) {
                            Icon(Icons.Default.Search, contentDescription = "Search games")
                        }
                    },
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
                    Text("The classics\nnever get old.", color = MaterialTheme.colorScheme.onPrimary, fontSize = 27.sp, lineHeight = 32.sp, fontWeight = FontWeight.ExtraBold)
                    Text("এক জায়গায় ফিরে পাও পুরোনো গেমের মজা।", color = SoftBlue)
                    TextButton(onClick = { onOpenLibrary("") }) {
                        Text("Explore Games  →", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        item {
            SectionHeading("Continue Playing", "চালিয়ে যাও", onOpenLibrary = { onOpenLibrary("") })
            GameRow("Snake Classic", "Arcade • Classic", "S", SoftBlue) {
                onOpenGame("snake-classic")
            }
        }
        item {
            SectionHeading("Popular Classics", "জনপ্রিয় গেম", onOpenLibrary = { onOpenLibrary("") })
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                GameRow("Brick Breaker", "Arcade • Skill", "B", SoftCoral) {
                    onOpenGame("brick-breaker")
                }
                GameRow("Space Shooter", "Action • Retro", "✦", SoftBlue) {
                    onOpenGame("space-shooter")
                }
                GameRow("Pong Classic", "Sports • 2 Player", "P", SoftCoral) {
                    onOpenGame("pong-classic")
                }
            }
        }
    }
}

@Composable
private fun SectionHeading(title: String, subtitle: String, onOpenLibrary: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, color = DeepNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(subtitle, color = Slate, fontSize = 12.sp)
        }
        TextButton(onClick = onOpenLibrary) {
            Text("See all", color = RoyalBlue, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun GameRow(
    title: String,
    category: String,
    symbol: String,
    tileColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            Text("Details  ›", color = RoyalBlue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SimpleSection(title: String, subtitle: String) {
    Column(
        modifier = Modifier
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
