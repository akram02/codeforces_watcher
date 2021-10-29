package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.h3,
    color: Color = MaterialTheme.colors.onBackground
) {
    Text(
        text = title,
        modifier = modifier.fillMaxWidth(),
        style = style,
        color = color
    )
}