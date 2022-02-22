package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.shared.PostInfo

@Composable
fun PostVideoView() = Column(
    modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
) {
    PostInfo()
    VideoView()
}

@Composable
private fun VideoView() = Image(
    painter = painterResource(R.drawable.video_placeholder),
    contentDescription = null,
    modifier = Modifier
        .aspectRatio(16f / 9)
        .clip(RoundedCornerShape(10.dp))
)