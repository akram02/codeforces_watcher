package com.bogdan.codeforceswatcher.features.news.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.UserAvatar

@Composable
fun PostInfo(
    title: String,
    authorAvatar: String,
    rankColor: Int,
    agoText: CharSequence
) = Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    UserAvatar(
        avatar = authorAvatar,
        modifier = Modifier.border(1.dp, colorResource(rankColor), CircleShape)
    )

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary,
            maxLines = 1
        )

        Text(
            text = AnnotatedString(agoText.toString()),
            style = AlgoismeTheme.typography.hintRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}