package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class ProblemsFiltersActivityNew : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlgoismeTheme {
                ProblemsFilters()
            }
        }
    }

    @Composable
    private fun ProblemsFilters() {
        val swipeRefreshState = rememberSwipeRefreshState(false)

        Box {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primaryVariant)
            )

            Column {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = { }
                ) {
                    FiltersList()
                }
            }
        }
    }

    @Composable
    private fun FiltersList() = LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        items(100) {
            FilterView()
        }
    }

    @Composable
    private fun FilterView() = Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "dfs and similar",
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_checkbox),
            contentDescription = "Checkbox",
            modifier = Modifier.clickable { }
        )
    }

    @Preview
    @Composable
    private fun Preview() = AlgoismeTheme {
        ProblemsFilters()
    }
}