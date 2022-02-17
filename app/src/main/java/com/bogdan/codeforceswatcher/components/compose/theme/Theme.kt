package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
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

private val LocalAlgoismeColors = staticCompositionLocalOf { commonAlgoismeColors }

private val commonAlgoismeColors = AlgoismeColors(
    primary = Color.Unspecified,
    primaryVariant = Color.Unspecified,
    secondary = Color.Unspecified,
    secondaryVariant = Color.Unspecified,
    background = Color.Unspecified,
    surface = Color.Unspecified,
    error = Color.Unspecified,
    onPrimary = Color.Unspecified,
    onSecondary = Color.Unspecified,
    onBackground = Color.Unspecified,
    onSurface = Color.Unspecified,
    onError = Color.Unspecified,

    onStar = Yellow
)

private val lightAlgoismeColors = with(commonAlgoismeColors) {
    copy(
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
    )
}

private val darkAlgoismeColors = with(commonAlgoismeColors) {
    copy(
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
        onSurface = White,
    )
}

private val LocalAlgoismeTypography = staticCompositionLocalOf { commonAlgoismeTypography }

val commonAlgoismeTypography = AlgoismeTypography(
    headerSmallMedium = TextStyle(
        fontWeight = FontWeight(500),
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp,
        letterSpacing = (-1).sp
    ),
    headerMiddleMedium = TextStyle(
        fontWeight = FontWeight(500),
        fontStyle = FontStyle.Normal,
        fontSize = 28.sp,
        letterSpacing = (-1).sp
    ),
    headerBigMedium = TextStyle(
        fontWeight = FontWeight(500),
        fontStyle = FontStyle.Normal,
        fontSize = 40.sp,
        letterSpacing = (-1).sp,
        textAlign = TextAlign.Start
    ),

    hintRegular = TextStyle(
        fontWeight = FontWeight(400),
        fontStyle = FontStyle.Normal,
        fontSize = 13.sp,
        letterSpacing = (-1).sp,
    ),
    hintSemiBold = TextStyle(
        fontWeight = FontWeight(600),
        fontStyle = FontStyle.Normal,
        fontSize = 13.sp,
        letterSpacing = (-1).sp
    ),

    primaryRegular = TextStyle(
        fontWeight = FontWeight(400),
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        letterSpacing = (-1).sp
    ),

    primarySemiBold = TextStyle(
        fontWeight = FontWeight(600),
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        letterSpacing = (-1).sp
    ),

    buttonSemiBold = TextStyle(
        fontWeight = FontWeight(600),
        fontStyle = FontStyle.Normal,
        fontSize = 16.sp,
        letterSpacing = (-1).sp,
        textAlign = TextAlign.Center
    ),
)

private val LocalAlgoismeShapes = staticCompositionLocalOf { commonAlgoismeShapes }

val commonAlgoismeShapes = AlgoismeShapes(
    small = RoundedCornerShape(100),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(0.dp)
)

//private val LocalColors = staticCompositionLocalOf { lightAlgoismeColors }

//@Composable
//fun AlgoismeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
//    val systemUiController = rememberSystemUiController()
//    val systemBarColor =
//        if (darkTheme) darkAlgoismeColor.primaryVariant else lightAlgoismeColors.primaryVariant
//    val useDarkIcons = MaterialTheme.colors.isLight
//
//    systemUiController.setSystemBarsColor(systemBarColor, useDarkIcons)
//
//    val colors = if (darkTheme) {
//        darkAlgoismeColor
//    } else {
//        lightAlgoismeColors
//    }
//
//    CompositionLocalProvider(LocalColors provides colors) {
//        MaterialTheme(
//            colors = colors,
//            typography = Typography,
//            shapes = Shapes,
//            content = content
//        )
//    }
//}