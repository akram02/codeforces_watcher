package com.bogdan.codeforceswatcher.features.problems

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun ProblemsView() {
    LazyColumn {
        items(100) {
            ProblemView()
        }
    }
}

@Composable
fun ProblemView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "1264A: Feeding Chicken",
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary
            )

            Text(
                text = "Codeforces Round #601 (Div. 1)",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.offset(y = (-6).dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(24.dp)
                .offset(y = (-6).dp)
        )
    }
}

@Preview
@Composable
fun ComposablePreview() {
    AlgoismeTheme {
        ProblemsView()
    }
}