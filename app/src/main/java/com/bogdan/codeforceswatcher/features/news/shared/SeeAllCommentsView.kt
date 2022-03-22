package com.bogdan.codeforceswatcher.features.news.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun SeeAllCommentsView(
    modifier: Modifier
) = Row(
    modifier = modifier,
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