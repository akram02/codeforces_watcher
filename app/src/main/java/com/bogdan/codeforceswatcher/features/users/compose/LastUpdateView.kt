package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun LastUpdateView(
    lastUpdate: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = lastUpdate,
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondaryVariant,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}