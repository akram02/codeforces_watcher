package com.bogdan.codeforceswatcher.features.news.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun PostContentView() = Column(
    modifier = Modifier
        .fillMaxWidth()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    PostInfo()

    Text(
        text = "A left-leaning red–black (LLRB) tree is a type of self-balancing binary search tree. It is a variant of the red–black tree and guarantees the same guarantees the same...",
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 4
    )
}