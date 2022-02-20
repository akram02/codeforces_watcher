package com.bogdan.codeforceswatcher.features.problems

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.WebViewActivity
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import com.bogdan.codeforceswatcher.R
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests
import io.xorum.codeforceswatcher.redux.analyticsController

class ProblemsFragment : Fragment(), StoreSubscriber<ProblemsState> {

    private val problemsState: MutableState<List<UIModel>> = mutableStateOf(emptyList())
    private val isRefreshState: SwipeRefreshState = SwipeRefreshState(false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    problemsState = problemsState,
                    isRefreshState = isRefreshState,
                    onProblem = { link, title ->
                        onProblem(link, title)
                    },
                    onStar = { id ->
                        onStar(id)
                    },
                    onRefresh = { onRefresh() },
                    onFilter = { onFilter() },
                    onSearch = { query ->
                        onSearch(query)
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.problems.status == newState.problems.status
                        && oldState.problems.isFavourite == newState.problems.isFavourite
                        && oldState.problems.filteredProblems == newState.problems.filteredProblems
            }.select { it.problems }
        }
    }

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this)
    }

    override fun onNewState(state: ProblemsState) {
        if (state.status == ProblemsState.Status.IDLE) {
            isRefreshState.isRefreshing = false
        }

        problemsState.value = state.filteredProblems.map {
            UIModel(
                it.id,
                it.title,
                it.subtitle,
                it.link,
                it.isFavourite
            )
        }
    }

    private fun onProblem(link: String, title: String) {
        startActivity(
            WebViewActivity.newIntent(
                requireContext(),
                link,
                title,
                AnalyticsEvents.PROBLEM_OPENED,
                AnalyticsEvents.PROBLEM_SHARED
            )
        )
    }

    private fun onStar(id: String) {
        store.dispatch(ProblemsRequests.ChangeStatusFavourite(id))
    }

    private fun onRefresh() {
        isRefreshState.isRefreshing = true

        store.dispatch(ProblemsRequests.FetchProblems(true))
        analyticsController.logEvent(AnalyticsEvents.PROBLEMS_REFRESH)
    }

    private fun onFilter() {
        startActivity(Intent(activity, ProblemsFiltersActivity::class.java))
    }

    private fun onSearch(query: String) {
        store.dispatch(ProblemsRequests.SetQuery(query))
    }
}

@Composable
private fun ContentView(
    problemsState: State<List<UIModel>>,
    isRefreshState: SwipeRefreshState,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit,
    onRefresh: () -> Unit,
    onFilter: () -> Unit,
    onSearch: (String) -> Unit
) = Scaffold(
    modifier = Modifier.fillMaxWidth(),
    topBar = { NavigationBar(onFilter, onSearch) },
    backgroundColor = AlgoismeTheme.colors.primaryVariant
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshState.isRefreshing),
        onRefresh = { onRefresh() }
    ) {
        ProblemsList(
            problemsState = problemsState,
            onProblem = onProblem,
            onStar = onStar
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NavigationBar(
    onFilter: () -> Unit,
    onSearch: (String) -> Unit
) {
    val localFocusManager = LocalFocusManager.current
    var isShownSearchTextFieldState by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = !isShownSearchTextFieldState,
            enter = fadeIn(),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.problems),
                style = AlgoismeTheme.typography.headerSmallMedium,
                color = AlgoismeTheme.colors.secondary
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Box(
                modifier = if (isShownSearchTextFieldState) Modifier.weight(1f) else Modifier.width(30.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                this@Row.AnimatedVisibility(
                    visible = isShownSearchTextFieldState,
                    enter = expandIn(
                        expandFrom = Alignment.TopEnd,
                        initialSize = { IntSize(30, 30) },
                        animationSpec = tween(
                            durationMillis = 150,
                            easing = LinearEasing
                        )
                    )
                ) {
                    SearchTextField(
                        modifier = Modifier.fillMaxWidth(1f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { localFocusManager.clearFocus() }
                        ),
                        onValueChange = { query ->
                            onSearch(query)
                        }
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_search_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable { isShownSearchTextFieldState = true }
                )
            }

            if (isShownSearchTextFieldState) {
                CrossButton {
                    isShownSearchTextFieldState = false
                    onSearch("")
                }
            } else {
                FilterButton { onFilter() }
            }
        }
    }
}

@Composable
private fun CrossButton(
    onCross: () -> Unit
) = Image(
    painter = painterResource(R.drawable.ic_cross_icon),
    contentDescription = null,
    modifier = Modifier.clickable { onCross() }
)

@Composable
private fun FilterButton(
    onFilter: () -> Unit
) = Image(
    painter = painterResource(R.drawable.ic_filter_icon),
    contentDescription = null,
    modifier = Modifier.clickable { onFilter() }
)

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }

    BasicTextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(it)
        },
        modifier = modifier
            .height(30.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(AlgoismeTheme.colors.primary)
            .padding(start = 10.dp, top = 4.dp, end = 0.dp, bottom = 0.dp),
        textStyle = AlgoismeTheme.typography.primaryRegular.copy(color = AlgoismeTheme.colors.onBackground),
        singleLine = true,
        cursorBrush = SolidColor(AlgoismeTheme.colors.onBackground),
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = stringResource(R.string.search_for_problems),
                    style = AlgoismeTheme.typography.primaryRegular,
                    color = AlgoismeTheme.colors.secondaryVariant
                )
            }
            innerTextField()
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

@Composable
private fun ProblemsList(
    problemsState: State<List<UIModel>>,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit,
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .clip(
            RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            )
        )
        .background(AlgoismeTheme.colors.primary)
) {
    items(problemsState.value) { problem ->
        ProblemView(
            problem = problem,
            onProblem = onProblem,
            onStar = onStar
        )
    }
}

@Composable
private fun ProblemView(
    problem: UIModel,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 0.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable { onProblem(problem.link, problem.title) }
    ) {
        Text(
            text = problem.title,
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary,
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = problem.subtitle,
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant,
            modifier = Modifier.height(16.dp)
        )
    }

    Icon(
        painter = painterResource(R.drawable.ic_star),
        contentDescription = null,
        tint = if (problem.isFavourite) AlgoismeTheme.colors.onStar else AlgoismeTheme.colors.secondary,
        modifier = Modifier
            .size(24.dp)
            .clickable { onStar(problem.id) }
    )
}

private data class UIModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val link: String,
    val isFavourite: Boolean
)