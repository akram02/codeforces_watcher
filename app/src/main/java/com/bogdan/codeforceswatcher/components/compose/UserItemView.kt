package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.UserItem
import io.xorum.codeforceswatcher.features.users.models.User

@Composable
fun UserItemView(
    userItem: UserItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(userItem.avatarLink) {
                transformations(
                    CircleCropTransformation()
                )
            },
            contentDescription = "avatar",
            modifier = Modifier.size(36.dp)
        )

        Spacer(Modifier.width(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = userItem.handle.toString(),
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onBackground,
                )

                Text(
                    text = userItem.dateOfLastRatingUpdate,
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = userItem.rating.toString(),
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onBackground,
                )
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    AlgoismeTheme { }
}