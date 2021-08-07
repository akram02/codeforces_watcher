package io.xorum.codeforceswatcher.features.problems.redux

import io.xorum.codeforceswatcher.features.problems.models.Problem
import tw.geothings.rekotlin.StateType

data class ProblemsState(
        internal val problems: List<Problem> = listOf(),
        val query: String = "",
        val filteredProblems: List<Problem> = listOf(),
        val status: Status = Status.IDLE,
        val isFavourite: Boolean = false,
        val tags: List<String> = listOf(),
        val selectedTags: Set<String> = setOf()
) : StateType {

    enum class Status { IDLE, PENDING }
}
