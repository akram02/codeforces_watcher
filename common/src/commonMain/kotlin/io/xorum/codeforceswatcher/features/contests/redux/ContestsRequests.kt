package io.xorum.codeforceswatcher.features.contests.redux

import io.xorum.codeforceswatcher.features.contests.ContestsRepository
import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.features.contests.response.ApiContest
import io.xorum.codeforceswatcher.util.Response
import io.xorum.codeforceswatcher.redux.*
import tw.geothings.rekotlin.Action

class ContestsRequests {

    class FetchContests(
        private val isInitiatedByUser: Boolean
    ) : Request() {

        private val contestsRepository: ContestsRepository = ContestsRepository()

        override suspend fun execute() {
            val result = when (val response = contestsRepository.getAll()) {
                is Response.Success -> Success(mapContests(response.result))
                is Response.Failure -> Failure(if (isInitiatedByUser) response.error.toMessage() else Message.None)
            }
            store.dispatch(result)
        }

        private fun mapContests(contests: List<ApiContest>) = contests.mapNotNull { it.toContest() }

        data class Success(val contests: List<Contest>) : Action
        data class Failure(override val message: Message) : ToastAction
    }

    class ChangeFilterCheckStatus(val platform: Contest.Platform, val isChecked: Boolean) : Action
}
