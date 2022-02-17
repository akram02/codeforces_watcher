package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun AvatarView(
    avatar: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(avatar),
        contentDescription = "avatar",
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun DefaultAvatarView(modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(R.drawable.ic_default_avatar),
        contentDescription = "avatar",
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape),
        tint = AlgoismeTheme.colors.secondaryVariant
    )
}