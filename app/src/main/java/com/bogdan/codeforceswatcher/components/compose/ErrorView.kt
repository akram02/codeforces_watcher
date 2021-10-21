package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow

@Composable
fun ErrorView(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colors.onError,
        style = MaterialTheme.typography.button.copy(
            shadow = Shadow(
                color = MaterialTheme.colors.error,
                blurRadius = 40f
            )
        ),
        modifier = Modifier.fillMaxWidth()
    )
}