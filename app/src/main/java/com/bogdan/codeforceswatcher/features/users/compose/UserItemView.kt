package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.*

@Composable
fun UserItemView(
    userItem: UserItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(
            avatar = userItem.avatar,
            modifier = Modifier.border(1.dp, colorResource(getColorByUserRank(userItem.rank)), CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Box(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = colorTextByUserRankNew(userItem.handle.toString(), userItem.rank),
                    style = AlgoismeTheme.typography.primarySemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )

                Text(
                    text = colorTextByUserRankNew(userItem.rating.toString(), userItem.rank),
                    style = AlgoismeTheme.typography.primarySemiBold,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Row(
                modifier = Modifier.align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = userItem.dateOfLastRatingUpdate,
                    style = AlgoismeTheme.typography.hintRegular,
                    color = AlgoismeTheme.colors.secondaryVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )

                Text(
                    text = when (userItem.update) {
                        Update.INCREASE -> "▲ " + userItem.lastRatingUpdate
                        Update.DECREASE -> "▼ " + userItem.lastRatingUpdate
                        else -> ""
                    },
                    style = AlgoismeTheme.typography.primarySemiBold.copy(fontSize = 13.sp),
                    color = when (userItem.update) {
                        Update.INCREASE -> AlgoismeTheme.colors.green
                        Update.DECREASE -> AlgoismeTheme.colors.red
                        else -> AlgoismeTheme.colors.secondaryVariant
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}