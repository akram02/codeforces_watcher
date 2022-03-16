package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.bogdan.codeforceswatcher.features.news.models.NewsItem

@Composable
fun PostFeedbackView(
    post: NewsItem.FeedbackItem,
    onPost: () -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .padding(top = 20.dp)
        .clip(AlgoismeTheme.shapes.medium)
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
) {
    Row(verticalAlignment = Alignment.Top) {
        Text(
            text = post.textTitle,
            style = AlgoismeTheme.typography.primaryRegular.copy(fontSize = 22.sp),
            color = AlgoismeTheme.colors.secondary,
            modifier = Modifier
                .weight(1f)
                .offset(y = (-4).dp)
        )

        Image(
            painter = painterResource(R.drawable.ic_cross_icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable { post.neutralButtonClick() }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallButton(
            label = post.textPositiveButton,
            isInverted = false
        ) {
            post.positiveButtonClick()
            onPost()
        }

        Text(
            text = post.textNegativeButton,
            style = AlgoismeTheme.typography.hintRegular.copy(fontWeight = FontWeight.W600),
            color = AlgoismeTheme.colors.secondary,
            modifier = Modifier.clickable {
                post.negativeButtonClick()
                onPost()
            }
        )
    }
}