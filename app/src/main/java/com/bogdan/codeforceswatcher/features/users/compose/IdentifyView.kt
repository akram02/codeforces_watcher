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
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.AuthButton
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun IdentifyView(modifier: Modifier = Modifier) {
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
                    .background(MaterialTheme.colors.background)
            )

            Text(
                text = "Who are you?",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Start
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Login to identify and get instant push notifications about rating updates",
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.widthIn(max = 208.dp)
                )

                AuthButton(
                    label = "LOGIN",
                    modifier = Modifier.defaultMinSize(minWidth = 80.dp, minHeight = 32.dp)
                ) {  }
            }
        }
    }
}
