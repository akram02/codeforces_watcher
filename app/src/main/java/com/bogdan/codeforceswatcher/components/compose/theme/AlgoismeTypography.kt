package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle

@Immutable
data class AlgoismeTypography(
    val headerSmallMedium: TextStyle,
    val headerMiddleMedium: TextStyle,
    val headerBigMedium: TextStyle,

    val hintRegular: TextStyle,
    val hintSemiBold: TextStyle,

    val primaryRegular: TextStyle,
    val primarySemiBold: TextStyle,

    val buttonSemiBold: TextStyle
)