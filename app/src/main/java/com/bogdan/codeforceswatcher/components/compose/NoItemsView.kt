package com.bogdan.codeforceswatcher.components.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun NoItemsView(
    @DrawableRes iconId: Int,
    @StringRes titleId: Int
) = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Image(
        painter = painterResource(iconId),
        contentDescription = null
    )

    Spacer(Modifier.height(32.dp))

    Text(
        text = stringResource(titleId),
        style = AlgoismeTheme.typography.headerSmallMedium.copy(fontWeight = FontWeight.W400),
        color = AlgoismeTheme.colors.secondaryVariant
    )
}