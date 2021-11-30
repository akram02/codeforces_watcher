package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.*
import com.bogdan.codeforceswatcher.features.users.buildRatingNew
import io.xorum.codeforceswatcher.features.users.models.User

@Composable
fun ProfileView(
    user: User,
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserAvatar(
                    avatar = user.avatar,
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, colorResource(getColorByUserRank(user.rank)), CircleShape)
                )

                Spacer(Modifier.width(20.dp))

                RatingData(user)
            }

            Box(Modifier.height(56.dp)) {
                Text(
                    text = user.handle,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.TopStart)
                )

                Text(
                    text = user.buildFullNameNew(),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.secondaryVariant,
                    maxLines = 1,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Composable
private fun RatingData(
    user: User,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        RatingDataItem(
            icon = R.drawable.ic_rating,
            description = "rating",
            caption = user.buildRatingNew()
        )

        RatingDataItem(
            icon = R.drawable.ic_flag,
            description = "maximal rating",
            caption = user.buildMaxRatingNew()
        )

        RatingDataItem(
            icon = R.drawable.ic_star,
            description = "contribution",
            caption = user.buildContributionNew()
        )
    }
}

@Composable
private fun RatingDataItem(
    icon: Int,
    description: String,
    caption: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        Icon(
            painter = painterResource(icon),
            contentDescription = description,
            tint = MaterialTheme.colors.onBackground
        )

        Spacer(Modifier.width(5.dp))

        Text(
            text = caption,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground
        )
    }
}