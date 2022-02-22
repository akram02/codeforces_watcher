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
import io.xorum.codeforceswatcher.features.news.models.News

@Composable
fun PostContentView(
    post: News.Post,
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
        author = post.author,
        title = post.title,
        modifiedAt = post.modifiedAt,
        isModified = post.isModified
    )

    Text(
        text = post.content,
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 4
    )
}