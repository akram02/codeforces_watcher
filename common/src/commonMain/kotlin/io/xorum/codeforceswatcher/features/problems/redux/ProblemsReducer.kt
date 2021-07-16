package io.xorum.codeforceswatcher.features.problems.redux

import io.xorum.codeforceswatcher.redux.states.AppState
import tw.geothings.rekotlin.Action

fun problemsReducer(action: Action, state: AppState): ProblemsState {
    var newState = state.problems

    when (action) {
        is ProblemsRequests.FetchProblems -> {
            newState = newState.copy(status = ProblemsState.Status.PENDING)
        }
        is ProblemsRequests.FetchProblems.Success -> {
            newState = newState.copy(
                    problems = action.problems,
                    tags = action.tags,
                    selectedTags = action.selectedTags,
                    status = ProblemsState.Status.IDLE
            )
        }
        is ProblemsRequests.FetchProblems.Failure -> {
            newState = newState.copy(status = ProblemsState.Status.IDLE)
        }
        is ProblemsActions.ChangeTypeProblems -> {
            newState = newState.copy(isFavourite = action.isFavourite)
        }
        is ProblemsRequests.ChangeStatusFavourite.Success -> {
            newState = newState.copy(problems = newState.problems.map {
                if (it.id == action.problem.id) action.problem else it
            })
        }
        is ProblemsRequests.ChangeTagCheckStatus -> {
            newState = newState.copy(
                    selectedTags = if (action.isChecked) newState.selectedTags.plus(action.tag)
                    else newState.selectedTags.minus(action.tag)
            )
        }
    }

    return newState
}
