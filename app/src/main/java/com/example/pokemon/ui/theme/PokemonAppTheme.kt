package com.example.pokemon

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PokemonAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val LightColors = lightColorScheme(
        primary = Color(0xFF6200EE),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFEADDFF),
        onPrimaryContainer = Color(0xFF1E0052),
        secondary = Color(0xFF03DAC6),
        onSecondary = Color.Black,
        secondaryContainer = Color(0xFFC7F8F9),
        onSecondaryContainer = Color(0xFF001F20),
        tertiary = Color(0xFFEFB81C),
        onTertiary = Color.Black,
        tertiaryContainer = Color(0xFFFCEBB4),
        onTertiaryContainer = Color(0xFF201B00),
        background = Color.White,
        onBackground = Color.Black,
        surface = Color.White,
        onSurface = Color.Black,
        inverseSurface = Color(0xFF303030),
        inverseOnSurface = Color(0xFFE6E6E6),
        error = Color(0xFFB00020),
        onError = Color.White,
        errorContainer = Color(0xFFF9DEDC),
        onErrorContainer = Color(0xFF410002)
    )

    val DarkColors = darkColorScheme(
        primary = Color(0xFFD5C6FF),
        onPrimary = Color(0xFF3B008C),
        primaryContainer = Color(0xFF5200C1),
        onPrimaryContainer = Color(0xFFEADDFF),
        secondary = Color(0xFFA3E9EA),
        onSecondary = Color(0xFF003738),
        secondaryContainer = Color(0xFF105F60),
        onSecondaryContainer = Color(0xFFC7F8F9),
        tertiary = Color(0xFFFFDDB6),
        onTertiary = Color(0xFF4E2B00),
        tertiaryContainer = Color(0xFF7E4D00),
        onTertiaryContainer = Color(0xFFFFDDB6),
        background = Color(0xFF101010),
        onBackground = Color(0xFFE6E6E6),
        surface = Color(0xFF101010),
        onSurface = Color(0xFFE6E6E6),
        inverseSurface = Color(0xFFE6E6E6),
        inverseOnSurface = Color(0xFF101010),
        error = Color(0xFFFFB4AB),
        onError = Color(0xFF690005),
        errorContainer = Color(0xFF93000A),
        onErrorContainer = Color(0xFFFFDAD6)
    )

    val colors = if (darkTheme) DarkColors else LightColors
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(8.dp),
        large = RoundedCornerShape(12.dp)
    )

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        shapes = shapes,
        content = content
    )
}