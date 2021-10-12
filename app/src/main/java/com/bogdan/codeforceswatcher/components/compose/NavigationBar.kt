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
import com.bogdan.codeforceswatcher.components.compose.theme.MUHeaderMedium18

@Composable
fun NavigationBar(title: String = "") {
    TopAppBar(
        title = { Text(
            text = title,
            style = MUHeaderMedium18
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
//        backgroundColor = White,
//        contentColor = Black,
        elevation = 0.dp,
        modifier = Modifier.height(56.dp)
    )
}