package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = CodGray,
    primaryVariant = MineShaft,
    secondary = White,
    secondaryVariant = SilverChalice,
    background = CodGray,
//    surface = ,
//    error = ,
    onPrimary = White,
    onSecondary = CodGray,
    onBackground = White,
//    onSurface = ,
//    onError =

)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = Gallery,
    secondary = Black,
    secondaryVariant = DoveGray,
    background = White,
//    surface = ,
//    error = ,
    onPrimary = Black,
    onSecondary = White,
    onBackground = Black,
//    onSurface = ,
//    onError =
)

@Composable
fun AlgoismeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
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