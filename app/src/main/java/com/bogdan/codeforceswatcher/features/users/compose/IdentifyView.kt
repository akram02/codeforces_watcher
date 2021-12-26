package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.SmallButton

@Composable
fun IdentifyView(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    Card(modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_no_avatar),
                contentDescription = null,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.primary)
            )

            Text(
                text = stringResource(R.string.who_are_you),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(R.string.login_to_identify_explanation),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.widthIn(max = 220.dp)
                )

                SmallButton(stringResource(R.string.login).uppercase()) { onButtonClick() }
            }
        }
    }
}
