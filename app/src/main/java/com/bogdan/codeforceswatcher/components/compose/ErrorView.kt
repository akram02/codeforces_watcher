package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun ErrorView(
    message: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = message,
        color = AlgoismeTheme.colors.onError,
        style = AlgoismeTheme.typography.buttonSemiBold.copy(
            shadow = Shadow(
                color = AlgoismeTheme.colors.error,
                blurRadius = 40f
            )
        ),
        modifier = modifier
    )
}