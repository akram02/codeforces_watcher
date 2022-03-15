package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.models.NewsItem
import com.bogdan.codeforceswatcher.features.news.shared.PostInfo

@Composable
fun PostVideoView(
    post: NewsItem.VideoItem,
    onPost: (String, String) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .wrapContentHeight()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(12.dp)
        .clickable { onPost(post.title, post.link) },
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    PostInfo(
        title = post.title,
        handle = post.authorHandle,
        avatar = post.authorAvatar,
        rank = post.authorRank,
        modifiedAt = post.createdAt
    )

    VideoView(post.thumbnailLink)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun VideoView(
    link: String
) = Image(
    painter = rememberImagePainter(
        data = link,
        builder = {
            error(R.drawable.video_placeholder)
        }
    ),
    contentDescription = null,
    modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 9)
        .height(180.dp)
        .clip(RoundedCornerShape(10.dp)),
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop
)