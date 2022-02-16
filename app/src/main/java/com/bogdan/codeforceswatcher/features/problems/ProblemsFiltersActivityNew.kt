package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.components.compose.NavigationBar
import com.bogdan.codeforceswatcher.features.filters.models.FilterItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class ProblemsFiltersActivityNew : AppCompatActivity(), StoreSubscriber<ProblemsState> {

    private val filtersState: MutableState<List<FilterItem>> = mutableStateOf(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AlgoismeTheme {
                ProblemsFilters(
                    filtersState = filtersState,
                    onFilter = { title, isChecked ->
                        onFilter(title = title, isChecked = isChecked)
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.problems.tags == newState.problems.tags
                        && oldState.problems.selectedTags == newState.problems.selectedTags
            }.select { it.problems }
        }
    }

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this)
    }

    override fun onNewState(state: ProblemsState) {
        filtersState.value = state.tags.toFilterItems(state)
    }

    private fun List<String>.toFilterItems(state: ProblemsState) = map { tag ->
        FilterItem(
            title = tag,
            imageId = null,
            isChecked = state.selectedTags.contains(tag),
        ) { isChecked ->
            store.dispatch(ProblemsRequests.ChangeTagCheckStatus(tag, isChecked))
        }
    }

    private fun onFilter(title: String, isChecked: Boolean) {
        store.dispatch(ProblemsRequests.ChangeTagCheckStatus(title, isChecked))
    }

    @Composable
    private fun ProblemsFilters(
        filtersState: State<List<FilterItem>>,
        onFilter: (String, Boolean) -> Unit
    ) {
        val swipeRefreshState = rememberSwipeRefreshState(false)

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar() },
            backgroundColor = MaterialTheme.colors.primary
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { }
            ) {
                FiltersList(
                    filtersState = filtersState,
                    onFilter = onFilter
                )
            }
        }
    }

    @Composable
    private fun TopBar() = NavigationBar(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        title = "Filters",
        onClick = { finish() }
    )

    @Composable
    private fun FiltersList(
        filtersState: State<List<FilterItem>>,
        onFilter: (String, Boolean) -> Unit
    ) = LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(filtersState.value) { filterItem ->
            FilterView(filterItem, onFilter)
        }
    }

    @Composable
    private fun FilterView(
        filterItem: FilterItem,
        onFilter: (String, Boolean) -> Unit
    ) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = filterItem.title,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.secondary
        )

        Icon(
            painter = painterResource(id = if (filterItem.isChecked) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled),
            contentDescription = null,
            modifier = Modifier.clickable {
                onFilter(filterItem.title, !filterItem.isChecked)
            }
        )
    }
}