package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = CodGray,
    primaryVariant = MineShaft,
    secondary = White,
    secondaryVariant = SilverChalice,
    background = CodGray,
    error = Red,
    onPrimary = White,
    onSecondary = CodGray,
    onBackground = White,
    onError = White
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = Gallery,
    secondary = Black,
    secondaryVariant = DoveGray,
    background = White,
    error = Red,
    onPrimary = Black,
    onSecondary = White,
    onBackground = Black,
    onError = Black
)

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

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}