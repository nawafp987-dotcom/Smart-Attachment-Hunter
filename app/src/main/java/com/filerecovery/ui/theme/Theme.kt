package com.filerecovery.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6C9EFF),
    onPrimary = Color(0xFF001855),
    primaryContainer = Color(0xFF0D3BA9),
    onPrimaryContainer = Color(0xFFD8E1FF),
    secondary = Color(0xFFBCC7FF),
    onSecondary = Color(0xFF001856),
    secondaryContainer = Color(0xFF1C2E8F),
    onSecondaryContainer = Color(0xFFD8E1FF),
    tertiary = Color(0xFFFFB8D1),
    onTertiary = Color(0xFF4C0E29),
    tertiaryContainer = Color(0xFF6B1B40),
    onTertiaryContainer = Color(0xFFFFD8EA),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000D),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0F1114),
    onBackground = Color(0xFFE8E8ED),
    surface = Color(0xFF191D22),
    onSurface = Color(0xFFE8E8ED),
    surfaceVariant = Color(0xFF49454E),
    onSurfaceVariant = Color(0xFFC9C7CF),
    outline = Color(0xFF938F99),
    outlineVariant = Color(0xFF49454E),
    scrim = Color(0xFF000000)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF0D3BA9),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD8E1FF),
    onPrimaryContainer = Color(0xFF001855),
    secondary = Color(0xFF1C2E8F),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD8E1FF),
    onSecondaryContainer = Color(0xFF001856),
    tertiary = Color(0xFF6B1B40),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFD8EA),
    onTertiaryContainer = Color(0xFF4C0E29),
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFF9DEDC),
    onErrorContainer = Color(0xFF410E0B),
    background = Color(0xFFFDFBFF),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFDFBFF),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFEAE7F0),
    onSurfaceVariant = Color(0xFF49454E),
    outline = Color(0xFF79747E),
    outlineVariant = Color(0xFFCAC7D0),
    scrim = Color(0xFF000000)
)

@Composable
fun FileRecoveryTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
