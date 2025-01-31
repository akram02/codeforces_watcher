package io.xorum.codeforceswatcher.util

import io.xorum.codeforceswatcher.db.DatabaseQueries
import io.xorum.codeforceswatcher.features.auth.models.getAuthStage
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.features.contests.redux.ContestsState
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.features.problems.redux.withFilteredProblems
import io.xorum.codeforceswatcher.features.problems.redux.withOrderedTags
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.redux.states.AppState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class PersistenceController : StoreSubscriber<AppState> {

    fun onAppCreated() {
        store.subscribe(this) {
            it.skipRepeats { oldState, newState ->
                oldState.users.sortType == newState.users.sortType &&
                        oldState.problems.isFavourite == newState.problems.isFavourite &&
                        oldState.contests.filters == newState.contests.filters &&
                        oldState.users.userAccount == newState.users.userAccount &&
                        oldState.problems.tags == newState.problems.tags &&
                        oldState.problems.selectedTags == newState.problems.selectedTags
            }
        }
    }

    fun fetchAppState() = AppState(
        contests = getContestsState(),
        users = getUsersState(),
        problems = getProblemsState(),
        auth = getAuthState()
    )

    private fun getContestsState() = ContestsState(
        filters = settings.readContestsFilters().map { Contest.Platform.valueOf(it) }.toMutableSet()
    )

    private fun getUsersState() = UsersState(
        followedUsers = DatabaseQueries.Users.getAll(),
        sortType = UsersState.SortType.getSortType(settings.readSpinnerSortPosition()),
        userAccount = settings.readUserAccount(),
    )

    private fun getProblemsState() = ProblemsState(
        problems = DatabaseQueries.Problems.getAll(),
        isFavourite = (settings.readProblemsIsFavourite()),
        tags = settings.readProblemsTags(),
        selectedTags = settings.readProblemsSelectedTags()
    ).withFilteredProblems().withOrderedTags()

    private fun getAuthState() = AuthState(authStage = settings.readUserAccount().getAuthStage())

    override fun onNewState(state: AppState) {
        settings.writeSpinnerSortPosition(state.users.sortType.position)
        settings.writeProblemsIsFavourite(state.problems.isFavourite)
        settings.writeContestsFilters(state.contests.filters.map { it.toString() }.toSet())
        settings.writeUserAccount(state.users.userAccount)
        settings.writeProblemsTags(state.problems.tags)
        settings.writeProblemsSelectedTags(state.problems.selectedTags)
    }
}
