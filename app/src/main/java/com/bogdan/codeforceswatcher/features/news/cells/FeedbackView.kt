package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.SmallButton

@Composable
fun FeedbackView() = Column(
    modifier = Modifier
        .padding(horizontal = 20.dp)
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Do you like algois.me?",
            style = AlgoismeTheme.typography.primaryRegular.copy(fontSize = 22.sp),
            color = AlgoismeTheme.colors.secondary
        )

        Image(
            painter = painterResource(R.drawable.ic_cross_icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallButton(
            label = "Yes!",
            isInverted = false
        ) { }

        Text(
            text = "Not exactly",
            style = AlgoismeTheme.typography.hintRegular.copy(fontWeight = FontWeight.W600),
            color = AlgoismeTheme.colors.secondary
        )
    }
}