package io.xorum.codeforceswatcher.redux.states

import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.features.contests.redux.ContestsState
import io.xorum.codeforceswatcher.features.news.redux.NewsState
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.features.verification.redux.VerificationState
import tw.geothings.rekotlin.StateType

data class AppState(
    val contests: ContestsState = ContestsState(),
    val users: UsersState = UsersState(),
    val news: NewsState = NewsState(),
    val problems: ProblemsState = ProblemsState(),
    val auth: AuthState = AuthState(),
    val verification: VerificationState = VerificationState()
) : StateType
