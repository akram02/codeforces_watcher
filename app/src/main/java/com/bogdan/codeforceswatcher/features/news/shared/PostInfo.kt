package com.bogdan.codeforceswatcher.features.news.shared

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun PostInfo() = Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
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
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Balanced left-leaning binary",
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary
        )

        Text(
            text = "yevhenii_kanivets · 15 minutes ago",
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}