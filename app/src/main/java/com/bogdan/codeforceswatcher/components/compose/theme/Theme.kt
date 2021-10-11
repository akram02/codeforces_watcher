package com.example.algoisme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorPalette = darkColors(
//    primary = MainNight,
//    primaryVariant = ,
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
//    primary = MainDay,
//    primaryVariant = ,
//    secondary = GrayDay,
//    secondaryVariant = ,
//    background = BackgroundDay,
//    surface = ,
//    error = ,
//    onPrimary = ,
//    onSecondary = ,
//    onBackground = ,
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