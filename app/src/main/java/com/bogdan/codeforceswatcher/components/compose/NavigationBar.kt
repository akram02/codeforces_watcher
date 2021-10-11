package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.example.algoisme.ui.theme.BackgroundDay
import com.example.algoisme.ui.theme.MainDay

@Composable
fun NavigationBar(title: String = "") {
    TopAppBar(
        title = { Text(title) },
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
        backgroundColor = BackgroundDay,
        contentColor = MainDay,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp)
    )
}