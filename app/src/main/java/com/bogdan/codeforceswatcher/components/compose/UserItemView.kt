package com.bogdan.codeforceswatcher.components.compose

import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.Update
import com.bogdan.codeforceswatcher.features.users.UserItem
import kotlinx.android.synthetic.main.view_user_item.view.*

@Composable
fun UserItemView(
    userItem: UserItem,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(R.drawable.ic_default_avatar),
            contentDescription = "avatar",
            tint = MaterialTheme.colors.secondaryVariant
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
                    style = MaterialTheme.typography.subtitle2.copy(
                        lineHeight = 15.sp
                    ),
                    color = MaterialTheme.colors.onBackground,
                )

                Text(
                    text = "Last active: ${userItem.lastRatingUpdate}",
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
                    lineHeight = 1.sp
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