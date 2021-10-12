package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.algoisme.ui.theme.Shapes

private val DarkColorPalette = darkColors(
//    primary = MainNight,
//    primaryVariant = GrayishForAccentsNight,
//    secondary = GrayNight,
//    secondaryVariant = ,
//    background = BackgroundNight,
//    surface = ,
//    error = ,
//    onPrimary = ,
//    onSecondary = ,
//    onBackground = ,
//    onSurface = ,
//    onError =

)

private val LightColorPalette = lightColors(
//    primary = White,
//    primaryVariant = Gallery,
//    secondary = DoveGray,
//    secondaryVariant = ,
//    background = White,
//    surface = ,
//    error = ,
//    onPrimary = Black,
//    onSecondary = ,
//    onBackground = Black,
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