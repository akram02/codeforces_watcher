package io.xorum.codeforceswatcher.features.problems.redux

import io.xorum.codeforceswatcher.db.DatabaseQueries
import io.xorum.codeforceswatcher.features.problems.ProblemsRepository
import io.xorum.codeforceswatcher.features.problems.models.Problem
import io.xorum.codeforceswatcher.features.problems.response.ApiProblem
import io.xorum.codeforceswatcher.redux.*
import io.xorum.codeforceswatcher.util.ProblemsDiff
import io.xorum.codeforceswatcher.util.Response
import tw.geothings.rekotlin.Action

class ProblemsRequests {

    class FetchProblems(private val isInitiatedByUser: Boolean) : Request() {

        private val problemsRepository: ProblemsRepository = ProblemsRepository()

        override suspend fun execute() {
            val result = when (val response = problemsRepository.getAll()) {
                is Response.Success -> {
                    val problems = mapProblems(response.result.problems)
                    val (toAddDiff, toUpdateDiff) = getDiff(problems)
                    updateDatabaseProblems(toAddDiff, toUpdateDiff)
                    Success(getAllProblems(), getMergedTags(response.result.tags))
                }
                is Response.Failure -> Failure(if (isInitiatedByUser) response.error.toMessage() else Message.None)
            }

            store.dispatch(result)
        }

        private fun mapProblems(problems: List<ApiProblem>): List<Problem> {
            val problemsMap =
                store.state.problems.problems.associateBy({ it.id }, { it.isFavourite })
            return problems.mapNotNull { it.toProblem(isFavourite = problemsMap[it.id] ?: false) }
        }

        private fun getDiff(newProblems: List<Problem>) =
            ProblemsDiff(store.state.problems.problems, newProblems).getDiff()

        private fun updateDatabaseProblems(toAddDiff: List<Problem>, toUpdateDiff: List<Problem>) {
            DatabaseQueries.Problems.update(toUpdateDiff)
            DatabaseQueries.Problems.insert(toAddDiff)
        }

        private fun getAllProblems() = DatabaseQueries.Problems.getAll()

        private fun getMergedTags(tags: List<String>) =
            store.state.problems.tags.plus(tags).distinct()

        data class Success(
            val problems: List<Problem>,
            val tags: List<String>
        ) : Action

        data class Failure(override val message: Message) : ToastAction
    }

    class ChangeStatusFavourite(private val problem: Problem) : Request() {

        override suspend fun execute() {
            val newProblem = problem.copy(isFavourite = !problem.isFavourite)
            DatabaseQueries.Problems.update(newProblem)
            store.dispatch(Success(newProblem))
        }

        data class Success(val problem: Problem) : Action
    }

    class ChangeTagCheckStatus(val tag: String, val isChecked: Boolean) : Action

    class SetQuery(val query: String) : Action
}
