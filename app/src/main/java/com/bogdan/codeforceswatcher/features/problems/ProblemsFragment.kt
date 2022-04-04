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
import androidx.compose.ui.graphics.ColorFilter
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
import com.bogdan.codeforceswatcher.components.compose.NoItemsView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.xorum.codeforceswatcher.features.problems.models.Problem
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests
import io.xorum.codeforceswatcher.redux.analyticsController

class ProblemsFragment : Fragment(), StoreSubscriber<ProblemsState> {

    private val problemsState: MutableState<ProblemsState?> = mutableStateOf(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    problemsState = problemsState,
                    onProblem = { link, title -> onProblem(link, title) },
                    onStar = { id -> onStar(id) },
                    onRefresh = { onRefresh() },
                    onFilter = { onFilter() },
                    onSearch = { query -> onSearch(query) }
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
        problemsState.value = state
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
    problemsState: State<ProblemsState?>,
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
    val state = problemsState.value ?: return@Scaffold

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.status != ProblemsState.Status.IDLE),
        onRefresh = { onRefresh() },
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
        if (state.filteredProblems.isEmpty()) {
            NoItemsView(
                iconId = R.drawable.ic_no_problems,
                titleId = R.string.problems_on_the_way
            )
        } else {
            ProblemsList(
                problemsState = state,
                onProblem = onProblem,
                onStar = onStar
            )
        }
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
            .padding(horizontal = 25.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isShownSearchTextFieldState) {
            Text(
                text = stringResource(R.string.problems),
                style = AlgoismeTheme.typography.headerSmallMedium,
                color = AlgoismeTheme.colors.secondary,
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            this@Row.AnimatedVisibility(
                visible = isShownSearchTextFieldState,
                enter = expandHorizontally(
                    animationSpec = tween(
                        durationMillis = 150,
                        easing = FastOutSlowInEasing
                    )
                ),
                exit = shrinkHorizontally(
                    animationSpec = tween(
                        durationMillis = 150,
                        easing = FastOutSlowInEasing
                    )
                )
            ) {
                SearchTextField(
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Ascii,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.clearFocus() }
                    ),
                    onValueChange = onSearch
                )
            }

            Image(
                painter = painterResource(R.drawable.ic_search_icon),
                contentDescription = null,
                modifier = Modifier.clickable { isShownSearchTextFieldState = true }
            )
        }

        Spacer(Modifier.width(20.dp))

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
private fun ProblemsList(
    problemsState: ProblemsState,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit,
) = LazyColumn {
    items(problemsState.filteredProblems) { problem ->
        ProblemView(
            problem = problem,
            onProblem = onProblem,
            onStar = onStar
        )
    }
}

@Composable
private fun ProblemView(
    problem: Problem,
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
        tint = if (problem.isFavourite) AlgoismeTheme.colors.yellow else AlgoismeTheme.colors.secondary,
        modifier = Modifier
            .size(24.dp)
            .clickable { onStar(problem.id) }
    )
}