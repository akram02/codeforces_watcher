package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
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
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun VerifyView(
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
                tint = AlgoismeTheme.colors.onBackground,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(AlgoismeTheme.colors.primary)
            )

            Text(
                text = stringResource(R.string.verify_account),
                style = AlgoismeTheme.typography.headerMiddleMedium,
                textAlign = TextAlign.Start
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = stringResource(R.string.pass_quick_verification),
                    style = AlgoismeTheme.typography.hintRegular,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.widthIn(max = 220.dp)
                )

                SmallButton(stringResource(R.string.verify).uppercase()) { onButtonClick() }
            }
        }
    }
}
