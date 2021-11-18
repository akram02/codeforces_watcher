package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.components.compose.theme.Green
import com.bogdan.codeforceswatcher.components.compose.theme.Red

@Composable
fun UserItem(
    modifier: Modifier = Modifier,
    avatar: Int = R.drawable.ic_default_avatar,
    name: String,
    lastActive: Int,
    rating: Int,
    contribution: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(avatar),
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
                    text = name,
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.subtitle2.copy(
                        lineHeight = 15.sp
                    ),
                    color = MaterialTheme.colors.onBackground,
                )

                Text(
                    text = "Last active: $lastActive",
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = rating.toString(),
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onBackground,
                    lineHeight = 1.sp
                )

                Text(
                    text = if (contribution >= 0) "▲ $contribution" else "▼ $contribution",
                    modifier = Modifier.padding(0.dp),
                    style = MaterialTheme.typography.subtitle2.copy(fontSize = 13.sp),
                    color = when {
                        contribution < 0 -> Red
                        contribution > 0 -> Green
                        else -> MaterialTheme.colors.secondaryVariant
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    AlgoismeTheme {
        UserItem(
            name = "Ilya",
            lastActive = 5,
            rating = 1000,
            contribution = 11
        )
    }
}