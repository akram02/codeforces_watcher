package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AlgoismeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = if (isDarkTheme) darkAlgoismeColors.primaryVariant else lightAlgoismeColors.primaryVariant

    systemUiController.setSystemBarsColor(systemBarColor, !isDarkTheme)

    CompositionLocalProvider(
        LocalAlgoismeColors provides if (isDarkTheme) darkAlgoismeColors else lightAlgoismeColors,
        LocalAlgoismeTypography provides commonAlgoismeTypography,
        LocalAlgoismeShapes provides commonAlgoismeShapes,
        content = content
    )
}

object AlgoismeTheme {

    val colors: AlgoismeColors
        @Composable
        get() = LocalAlgoismeColors.current

    val typography: AlgoismeTypography
        @Composable
        get() = LocalAlgoismeTypography.current

    val shapes: AlgoismeShapes
        @Composable
        get() = LocalAlgoismeShapes.current
}

// Colors

private val LocalAlgoismeColors = staticCompositionLocalOf { commonAlgoismeColors }

private val commonAlgoismeColors = AlgoismeColors(
    primary = Color.Unspecified,
    primaryVariant = Color.Unspecified,
    secondary = Color.Unspecified,
    secondaryVariant = Color.Unspecified,
    background = Color.Unspecified,
    surface = Color.Unspecified,
    error = Color.Unspecified,
    onBackground = Color.Unspecified,
    onError = Color.Unspecified,

    lightGray = Color.Unspecified,

    white = Color(0xFFFFFFFF),
    black = Color(0xFF000000),
    accentGrayish = Color(0xFFEEEEEE),
    mineShaft = Color(0xFF303030),
    red = Color(0xFFFF0000),
    green = Color(0xFF05D200),

    onStar = Color(0xFFFFCA00),

    transparent = Color.Transparent
)

private val lightAlgoismeColors = with(commonAlgoismeColors) {
    copy(
        primary = white,
        primaryVariant = accentGrayish,

        secondary = black,
        secondaryVariant = Color(0xFF636363),

        background = white,
        surface = accentGrayish,
        error = red,

        onBackground = black,
        onError = black,

        lightGray = Color(0xFFF5F5F5)
    )
}

private val darkAlgoismeColors = with(commonAlgoismeColors) {
    copy(
        primary = black,
        primaryVariant = mineShaft,

        secondary = white,
        secondaryVariant = Color(0xFFAAAAAA),

        background = black,
        surface = mineShaft,
        error = red,

        onBackground = white,
        onError = white,

        lightGray = Color(0xFF222222)
    )
}

// Fonts

private val LocalAlgoismeTypography = staticCompositionLocalOf { commonAlgoismeTypography }

private val commonAlgoismeTypography = AlgoismeTypography(
    headerSmallMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 18.sp,
        letterSpacing = (-1).sp
    ),
    headerMiddleMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 28.sp,
        letterSpacing = (-1).sp
    ),
    headerBigMedium = TextStyle(
        fontWeight = FontWeight.W500,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 40.sp,
        letterSpacing = (-1).sp,
        textAlign = TextAlign.Start
    ),

    hintRegular = TextStyle(
        fontWeight = FontWeight.W400,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 13.sp,
        letterSpacing = (-1).sp,
    ),
    hintSemiBold = TextStyle(
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 13.sp,
        letterSpacing = (-1).sp
    ),

    primaryRegular = TextStyle(
        fontWeight = FontWeight.W400,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 16.sp,
        letterSpacing = (-1).sp
    ),

    primarySemiBold = TextStyle(
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 16.sp,
        letterSpacing = (-1).sp
    ),

    buttonSemiBold = TextStyle(
        fontWeight = FontWeight.W600,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        fontSize = 16.sp,
        letterSpacing = (-1).sp,
        textAlign = TextAlign.Center
    ),
)

// Shapes

private val LocalAlgoismeShapes = staticCompositionLocalOf { commonAlgoismeShapes }

private val commonAlgoismeShapes = AlgoismeShapes(
    small = RoundedCornerShape(100),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(0.dp)
)