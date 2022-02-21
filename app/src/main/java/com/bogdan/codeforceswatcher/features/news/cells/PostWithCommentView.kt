package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.shared.PostInfo

@Composable
fun PostWithCommentView() = Column(
    modifier = Modifier
        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp)
        .clip(RoundedCornerShape(50f))
        .background(AlgoismeTheme.colors.lightGray),
    verticalArrangement = Arrangement.spacedBy(10.dp)
) {
    PostView()
    CommentView()
    SeeAllCommentsView()
}

@Composable
private fun PostView() = Column(
    modifier = Modifier
        .fillMaxWidth()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(12.dp)
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

@Composable
private fun CommentView() = Row(
    modifier = Modifier
        .padding(horizontal = 12.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
) {
    Icon(
        painter = painterResource(R.drawable.ic_default_avatar),
        contentDescription = null,
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape),
        tint = AlgoismeTheme.colors.secondaryVariant
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 15.dp,
                    bottomEnd = 15.dp,
                    bottomStart = 15.dp
                )
            )
            .background(AlgoismeTheme.colors.primaryVariant)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = "BOHDAN_ · 7 minutes ago",
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant
        )

        Text(
            text = "It's not that interesting really. It's O(n) running time to delete...",
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
private fun SeeAllCommentsView() = Row(
    modifier = Modifier
        .fillMaxWidth()
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(horizontal = 12.dp)
        .height(32.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = "See all comments",
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondaryVariant,
    )

    Icon(
        painter = painterResource(R.drawable.ic_arrow),
        contentDescription = null,
        tint = AlgoismeTheme.colors.secondaryVariant
    )
}