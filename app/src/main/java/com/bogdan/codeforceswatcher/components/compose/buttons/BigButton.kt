package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BigButton(
    label: String,
    modifier: Modifier = Modifier,
    isInverted: Boolean = false,
    action: () -> Unit
) {
    CommonButton(
        label = label,
        modifier = modifier.defaultMinSize(minWidth = 250.dp, minHeight = 40.dp),
        textStyle = MaterialTheme.typography.button,
        backgroundColor = if (isInverted) Color.Transparent else MaterialTheme.colors.secondary,
        borderStroke = if (isInverted) BorderStroke(2.dp, MaterialTheme.colors.secondary) else null
    ) {
        action()
    }
}