package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.Black
import com.bogdan.codeforceswatcher.components.compose.theme.Green
import com.bogdan.codeforceswatcher.components.compose.theme.Red
import com.bogdan.codeforceswatcher.features.users.Update
import com.bogdan.codeforceswatcher.features.users.UserItem
import com.bogdan.codeforceswatcher.features.users.getColorByUserRank

@Composable
fun UserItemView(
    userItem: UserItem,
    modifier: Modifier = Modifier
) {
    val avatar = if(userItem.avatarLink == "https://userpic.codeforces.org/no-avatar.jpg") {
        R.drawable.ic_default_avatar
    } else {
        userItem.avatarLink
    }

    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(avatar) { transformations(CircleCropTransformation()) },
            contentDescription = "avatar",
            modifier = Modifier
                .size(36.dp)
                .border(1.dp, colorResource(getColorByUserRank(userItem.rank)), CircleShape)
        )

        Spacer(Modifier.width(8.dp))

        Box(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = colorTextByUserRank(userItem.handle.toString(), userItem.rank),
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(0.85f)
                )

                Text(
                    text = colorTextByUserRank(userItem.rating.toString(), userItem.rank),
                    style = MaterialTheme.typography.subtitle2,
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
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondaryVariant,
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
                    style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp),
                    color = when (userItem.update) {
                        Update.INCREASE -> Green
                        Update.DECREASE -> Red
                        else -> MaterialTheme.colors.secondaryVariant
                    },
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun colorTextByUserRank(text: String, rank: String?) = buildAnnotatedString {
    if (listOf("legendary grandmaster", "легендарный гроссмейстер").contains(rank)) {
        withStyle(SpanStyle(color = Black)) { append(text[0]) }
        withStyle(SpanStyle(color = colorResource(getColorByUserRank(rank)))) {
            append(text.substring(1, text.length))
        }
    } else {
        withStyle(SpanStyle(color = colorResource(getColorByUserRank(rank)))) {
            append(text)
        }
    }
}