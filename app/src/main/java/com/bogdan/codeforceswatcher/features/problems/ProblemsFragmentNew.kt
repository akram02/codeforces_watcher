package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.WebViewActivity
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import com.bogdan.codeforceswatcher.R
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests

class ProblemsFragmentNew : Fragment(), StoreSubscriber<ProblemsState> {

    private var problemsState: MutableState<List<UIModel>> = mutableStateOf(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ProblemsView(
                    problemsState = problemsState,
                    onProblem = { link, title ->
                        onProblem(link, title)
                    },
                    onStar = { id ->
                        onStar(id)
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
}

@Composable
private fun ProblemsView(
    problemsState: State<List<UIModel>>,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primaryVariant)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colors.primary)
        ) {
            items(problemsState.value) { problem ->
                ProblemView(
                    problem = problem,
                    onProblem = onProblem,
                    onStar = onStar
                )
            }
        }
    }
}

@Composable
private fun ProblemView(
    problem: UIModel,
    onProblem: (String, String) -> Unit,
    onStar: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onProblem(problem.link, problem.title) }
        ) {
            Text(
                text = problem.title,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = problem.subtitle,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.height(16.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = null,
            tint = colorResource(if (problem.isFavourite) R.color.colorAccent else R.color.black),
            modifier = Modifier
                .size(24.dp)
                .clickable { onStar(problem.id) }
        )
    }
}

private data class UIModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val link: String,
    val isFavourite: Boolean
)