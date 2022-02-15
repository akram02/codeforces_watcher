package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = Colors(
    material = darkColors(
        primary = CodGray,
        primaryVariant = MineShaft,
        secondary = White,
        secondaryVariant = SilverChalice,
        background = CodGray,
        error = Red,
        surface = MineShaft,
        onPrimary = White,
        onSecondary = CodGray,
        onBackground = White,
        onError = White,
        onSurface = White
    ),
    yellow = Yellow
)

private val LightColorPalette = Colors(
    material = lightColors(
        primary = White,
        primaryVariant = Gallery,
        secondary = Black,
        secondaryVariant = DoveGray,
        background = White,
        error = Red,
        surface = Gallery,
        onPrimary = Black,
        onSecondary = White,
        onBackground = Black,
        onError = Black,
        onSurface = Black
    ),
    yellow = Yellow
)

private val LocalColors = staticCompositionLocalOf { LightColorPalette }

@Composable
fun AlgoismeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor =
        if (darkTheme) DarkColorPalette.primaryVariant else LightColorPalette.primaryVariant
    val useDarkIcons = MaterialTheme.colors.isLight

    systemUiController.setSystemBarsColor(systemBarColor, useDarkIcons)

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val MaterialTheme.palette: Colors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current