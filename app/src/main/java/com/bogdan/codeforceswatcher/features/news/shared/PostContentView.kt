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
fun PostContentView(
    title: String,
    content: String,
    authorAvatar: String,
    rankColor: Int,
    agoText: CharSequence,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    PostInfo(
        title = title,
        authorAvatar = authorAvatar,
        rankColor = rankColor,
        agoText = agoText
    )

    Text(
        text = content,
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 4
    )
}