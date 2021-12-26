package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SmallButton(
    label: String,
    modifier: Modifier = Modifier,
    isInverted: Boolean = true,
    action: () -> Unit
) {
    CommonButton(
        label = label,
        modifier = modifier.defaultMinSize(minWidth = 80.dp, minHeight = 32.dp),
        textStyle = MaterialTheme.typography.button.copy(fontSize = 13.sp),
        backgroundColor = if (isInverted) Color.Transparent else MaterialTheme.colors.secondary,
        borderStroke = if (isInverted) BorderStroke(1.6.dp, MaterialTheme.colors.secondary) else null
    ) {
        action()
    }
}