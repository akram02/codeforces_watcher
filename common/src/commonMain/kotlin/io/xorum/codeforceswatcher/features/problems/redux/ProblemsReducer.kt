package io.xorum.codeforceswatcher.features.problems.redux

import io.xorum.codeforceswatcher.redux.states.AppState
import tw.geothings.rekotlin.Action

fun problemsReducer(action: Action, state: AppState): ProblemsState {
    var newState = state.problems

    when (action) {
        is ProblemsRequests.FetchProblems -> {
            newState = newState.copy(
                status = ProblemsState.Status.PENDING
            )
        }
        is ProblemsRequests.FetchProblems.Success -> {
            newState = newState.copy(
                problems = action.problems,
                tags = action.tags,
                status = ProblemsState.Status.IDLE
            ).withOrderedTags().withFilteredProblems()
        }
        is ProblemsRequests.FetchProblems.Failure -> {
            newState = newState.copy(
                status = ProblemsState.Status.IDLE
            )
        }
        is ProblemsActions.ChangeTypeProblems -> {
            newState = newState.copy(
                isFavourite = action.isFavourite
            ).withFilteredProblems()
        }
        is ProblemsRequests.ChangeStatusFavourite.Success -> {
            newState = newState.copy(
                problems = newState.problems.map {
                    if (it.id == action.problem.id) action.problem else it
                }
            ).withFilteredProblems()
        }
        is ProblemsRequests.ChangeTagCheckStatus -> {
            newState = newState.copy(
                selectedTags = if (action.isChecked) newState.selectedTags.plus(action.tag)
                else newState.selectedTags.minus(action.tag)
            ).withFilteredProblems()
        }
        is ProblemsRequests.SetQuery -> {
            newState = newState.copy(
                query = action.query
            ).withFilteredProblems()
        }
    }

    return newState
}

fun ProblemsState.withFilteredProblems() = copy(
    filteredProblems = problems.filter { if (isFavourite) it.isFavourite else true }
        .filter { it.tags.containsAll(selectedTags) }
        .filter {
            it.title.toLowerCase().kmpContains(query.toLowerCase())
                    || it.subtitle.toLowerCase().kmpContains(query.toLowerCase())
        }
        .sortedByDescending { it.createdAtMillis }
)

fun ProblemsState.withOrderedTags() =
    copy(tags = tags.sortedWith(compareBy({ it.priority }, { it })))

private val String.priority
    get() = toIntOrNull() ?: Int.MAX_VALUE

private fun String.kmpContains(searchString: String): Boolean {
    val findingStringLength = searchString.length
    val cmpStr = "$searchString%$this"
    val prefixArray = IntArray(cmpStr.length)
    var currentIndexOfBlock = 0
    prefixArray[0] = 0
    for (i in 1 until cmpStr.length) {
        while (currentIndexOfBlock > 0 && cmpStr[currentIndexOfBlock] != cmpStr[i]) {
            currentIndexOfBlock = prefixArray[currentIndexOfBlock - 1]
        }
        if (cmpStr[currentIndexOfBlock] == cmpStr[i]) {
            currentIndexOfBlock++
        }

        prefixArray[i] = currentIndexOfBlock
        if (prefixArray[i] == findingStringLength) {
            return true
        }
    }
    return false
}