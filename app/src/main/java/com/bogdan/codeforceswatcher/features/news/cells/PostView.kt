package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.models.NewsItem
import com.bogdan.codeforceswatcher.features.news.shared.PostContentView
import com.bogdan.codeforceswatcher.features.news.shared.SeeAllCommentsView

@Composable
fun PostView(
    post: NewsItem.PostItem,
    onPost: (String, String) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .padding(top = 20.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(AlgoismeTheme.colors.lightGray)
        .clickable { onPost(post.link, post.blogTitle) }
) {
    PostContentView(
        title = post.blogTitle,
        content = post.content,
        handle = post.authorHandle,
        avatar = post.authorAvatar,
        rank = post.authorRank,
        modifiedAt = post.modifiedAt,
        isModified = post.isModified
    )

    SeeAllCommentsView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .height(32.dp)
    )
}