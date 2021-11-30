package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.features.users.getColorByUserRank

@Composable
fun AvatarView(
    avatar: String,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberImagePainter(avatar) { transformations(CircleCropTransformation()) },
        contentDescription = "avatar",
        modifier = modifier.size(36.dp)
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
        tint = MaterialTheme.colors.secondaryVariant
    )
}