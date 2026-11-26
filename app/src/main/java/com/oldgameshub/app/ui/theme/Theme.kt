package com.oldgameshub.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val OldGamesColorScheme = lightColorScheme(
    primary = RoyalBlue,
    onPrimary = PureWhite,
    secondary = Coral,
    onSecondary = PureWhite,
    background = WarmIvory,
    onBackground = DeepNavy,
    surface = PureWhite,
    onSurface = DeepNavy,
    surfaceVariant = SoftBlue,
    onSurfaceVariant = Slate,
    outline = BorderCream
)

@Composable
fun OldGamesHubTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = OldGamesColorScheme,
        content = content
    )
}
