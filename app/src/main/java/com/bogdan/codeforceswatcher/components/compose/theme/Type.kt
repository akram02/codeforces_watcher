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
    fontWeight = FontWeight(500),
    fontStyle = FontStyle.Normal,
    fontSize = 18.sp,
    letterSpacing = (-1).sp
)
val MUHeaderMedium28 = TextStyle(
    fontWeight = FontWeight(500),
    fontStyle = FontStyle.Normal,
    fontSize = 28.sp,
    letterSpacing = (-1).sp
)
val MUHeaderMedium40 = TextStyle(
    fontWeight = FontWeight(500),
    fontStyle = FontStyle.Normal,
    fontSize = 40.sp,
    letterSpacing = (-1).sp,
    textAlign = TextAlign.Start
)
val MUHintRegular13 = TextStyle(
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    fontSize = 13.sp,
    letterSpacing = (-1).sp,
)
val MUHintBoldSemiBold13 = TextStyle(
    fontWeight = FontWeight(600),
    fontStyle = FontStyle.Normal,
    fontSize = 13.sp,
    letterSpacing = (-1).sp,
    textDecoration = TextDecoration.Underline
)

val MUPrimaryRegular16 = TextStyle(
    fontWeight = FontWeight(400),
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    letterSpacing = (-1).sp
)

val MUPrimarySemiBold16 = TextStyle(
    fontWeight = FontWeight(600),
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    letterSpacing = (-1).sp
)

val MUButtonSemiBold16 = TextStyle(
    fontWeight = FontWeight(600),
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    letterSpacing = (-1).sp,
    textAlign = TextAlign.Center
)

val Typography = Typography(
    defaultFontFamily = sfMono,
    h3 = MUHeaderMedium40,
    h5 = MUHeaderMedium28,
    h6 = MUHeaderMedium18,
    caption = MUHintRegular13,
    subtitle1 = MUPrimaryRegular16,
    subtitle2 = MUPrimarySemiBold16,
    button = MUButtonSemiBold16,
    body1 = MUHintRegular13,
    body2 = MUHintBoldSemiBold13,
)