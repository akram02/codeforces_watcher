package com.example.algoisme.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R

val sfMono = FontFamily(
    Font(R.font.sf_mono_bold, FontWeight.Bold),
    Font(R.font.sf_mono_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.sf_mono_heavy, FontWeight.ExtraBold),
    Font(R.font.sf_mono_heavy_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.sf_mono_light, FontWeight.Light),
    Font(R.font.sf_mono_light_ltalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.sf_mono_medium, FontWeight.Medium),
    Font(R.font.sf_mono_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.sf_mono_regular, FontWeight.Normal),
    Font(R.font.sf_mono_regular_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.sf_mono_semibold, FontWeight.SemiBold),
    Font(R.font.sf_mono_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),
)

val defaultTextStyle = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(400),
    fontSize = 16.sp,
    letterSpacing = (-1).sp,
    color = MainDay
)

val Typography = Typography(
    defaultFontFamily = sfMono,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        letterSpacing = (-1).sp
    )
)