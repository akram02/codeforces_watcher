package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R

@Composable
fun NavigationBar(title: String = "") {
    TopAppBar(
        title = { Text(
            text = title,
            style = MaterialTheme.typography.h6
        ) },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_path),
                    contentDescription = null
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp)
    )
}