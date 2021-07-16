package io.xorum.codeforceswatcher.features.contests.redux

import io.xorum.codeforceswatcher.features.contests.models.Contest
import tw.geothings.rekotlin.StateType

data class ContestsState(
        val status: Status = Status.IDLE,
        val contests: List<Contest> = listOf(),
        val filters: Set<Contest.Platform> = setOf()
) : StateType {

    enum class Status { IDLE, PENDING }
}
