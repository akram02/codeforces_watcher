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
import com.bogdan.codeforceswatcher.components.WebViewActivity
import io.xorum.codeforceswatcher.util.AnalyticsEvents

class ProblemsFragmentNew : Fragment(), StoreSubscriber<ProblemsState> {

    private var problemsState: MutableState<List<ProblemUIModel>> = mutableStateOf(emptyList())

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
            ProblemUIModel(
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
}