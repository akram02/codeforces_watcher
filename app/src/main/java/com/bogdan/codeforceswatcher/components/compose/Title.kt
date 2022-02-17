package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = AlgoismeTheme.typography.headerBigMedium,
    color: Color = AlgoismeTheme.colors.onBackground
) {
    Text(
        text = title,
        modifier = modifier.fillMaxWidth(),
        style = style,
        color = color
    )
}