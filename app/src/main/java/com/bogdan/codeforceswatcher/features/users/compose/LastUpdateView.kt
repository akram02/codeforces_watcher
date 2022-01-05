package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun LastUpdateView(
    lastUpdate: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = lastUpdate,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.secondaryVariant,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}