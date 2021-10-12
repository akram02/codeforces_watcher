package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

val MUHeaderMedium18 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(500),
    fontStyle = FontStyle.Normal,
    fontSize = 18.sp,
    lineHeight = (21.48).sp,
    letterSpacing = (-1).sp
)
val MUBigHeaderMedium40 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(500),
    fontStyle = FontStyle.Normal,
    fontSize = 40.sp,
    lineHeight = 40.sp,
    letterSpacing = (-1).sp,
    textAlign = TextAlign.Start
)
val MUHintRegular13 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    lineHeight = (16.41).sp,
    letterSpacing = (-1).sp,
)
val MUHintBoldSemiBold13 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(600),
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    lineHeight = (16.41).sp,
    letterSpacing = (-1).sp,
    textAlign = TextAlign.Center,
    textDecoration = TextDecoration.Underline
)
val MUPrimaryRegular16 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    lineHeight = (19.09).sp,
    letterSpacing = (-1).sp,
)
val MUButtonSemiBold16 = TextStyle(
    fontFamily = sfMono,
    fontWeight = FontWeight(600),
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    lineHeight = (19.09).sp,
    letterSpacing = (-1).sp,
    textAlign = TextAlign.Center
)

//val defaultTextStyle = TextStyle(
//    fontFamily = sfMono,
//    fontWeight = FontWeight(400),
//    fontSize = 16.sp,
//    letterSpacing = (-1).sp,
//)
val Typography = Typography(
//    defaultFontFamily = sfMono,
//    body1 = TextStyle(
//        fontWeight = FontWeight.Normal,
//        letterSpacing = (-1).sp
//    )
)