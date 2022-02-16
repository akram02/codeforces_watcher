package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primary,
    title: String = "",
    navigationIcon: Int = R.drawable.ic_path,
    navigationIconDescription: String? = null,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    painter = painterResource(navigationIcon),
                    contentDescription = navigationIconDescription
                )
            }
        },
        backgroundColor = backgroundColor,
        elevation = 0.dp,
        modifier = modifier
    )
}