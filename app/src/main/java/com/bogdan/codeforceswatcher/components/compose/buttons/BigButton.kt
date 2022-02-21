package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

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
        textStyle = AlgoismeTheme.typography.buttonSemiBold,
        backgroundColor = if (isInverted) AlgoismeTheme.colors.transparent else AlgoismeTheme.colors.secondary,
        labelColor = if (isInverted) AlgoismeTheme.colors.secondary else AlgoismeTheme.colors.primary,
        borderStroke = if (isInverted) BorderStroke(2.dp, AlgoismeTheme.colors.secondary) else null
    ) {
        action()
    }
}