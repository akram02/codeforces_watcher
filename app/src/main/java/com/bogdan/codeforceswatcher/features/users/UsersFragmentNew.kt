package com.bogdan.codeforceswatcher.features.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.auth.SignInComposeActivity
import com.bogdan.codeforceswatcher.features.auth.VerificationComposeActivity
import com.bogdan.codeforceswatcher.features.users.compose.ProfileItemView
import com.bogdan.codeforceswatcher.features.users.compose.UserItemView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.users.models.User
import io.xorum.codeforceswatcher.features.users.redux.UsersActions
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.redux.states.AppState
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import tw.geothings.rekotlin.StoreSubscriber

class UsersFragmentNew : Fragment(), StoreSubscriber<AppState> {

    private val appState: MutableState<AppState?> = mutableStateOf(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    appState = appState,
                    modifier = Modifier.fillMaxSize(),
                    onLoginButtonClick = { startSignInActivity() },
                    onVerifyButtonClick = { startVerifyActivity() },
                    onViewProfileButtonClick = { userAccount -> startUserAccountActivity(userAccount) },
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.users == newState.users && oldState.auth == newState.auth
            }
        }
    }

    override fun onStop() {
        super.onStop()
        store.unsubscribe(this)
    }

    private fun startSignInActivity() {
        startActivity(Intent(context, SignInComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.SIGN_IN_OPENED)
    }

    private fun startVerifyActivity() {
        startActivity(Intent(context, VerificationComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.VERIFY_OPENED)
    }

    private fun startUserAccountActivity(userAccount: UserAccount?) {
        startActivity(context?.let {
            UserActivity.newIntent(it, userAccount?.codeforcesUser?.handle!!, true)
        })
    }

    override fun onNewState(state: AppState) {
        appState.value = state

        if (state.users.addUserStatus == UsersState.Status.DONE) {
            store.dispatch(UsersActions.ClearAddUserState())
        }
    }
}

@Composable
private fun ContentView(
    appState: State<AppState?>,
    modifier: Modifier = Modifier,
    onLoginButtonClick: () -> Unit,
    onVerifyButtonClick: () -> Unit,
    onViewProfileButtonClick: (UserAccount?) -> Unit
) = Scaffold(
    backgroundColor = AlgoismeTheme.colors.primaryVariant
) {
    val state = appState.value ?: return@Scaffold
    val users = state.users.followedUsers.sort(state.users.sortType).map { UserItem(it) }

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.users.status != UsersState.Status.IDLE),
        onRefresh = { },
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(AlgoismeTheme.colors.primary)
    ) {
        LazyColumn {
            item {
                ProfileItemView(
                    userAccount = state.users.userAccount,
                    authStage = state.auth.authStage,
                    onLoginButtonClick = onLoginButtonClick,
                    onVerifyButtonClick = onVerifyButtonClick,
                    onViewProfileButtonClick = onViewProfileButtonClick,
                )
            }

            item {
                Text(
                    text = stringResource(R.string.users),
                    style = AlgoismeTheme.typography.hintSemiBold.copy(fontSize = 20.sp),
                    color = AlgoismeTheme.colors.secondary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            items(users) {
                UserItemView(it, Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
            }
        }
    }
}

private fun List<User>.sort(sortType: UsersState.SortType) = when (sortType) {
    UsersState.SortType.DEFAULT -> reversed()
    UsersState.SortType.RATING_DOWN -> sortedByDescending(User::rating)
    UsersState.SortType.RATING_UP -> sortedBy(User::rating)
    UsersState.SortType.UPDATE_DOWN -> sortedByDescending { user ->
        user.ratingChanges.lastOrNull()?.ratingUpdateTimeSeconds
    }
    UsersState.SortType.UPDATE_UP -> sortedBy { user ->
        user.ratingChanges.lastOrNull()?.ratingUpdateTimeSeconds
    }
}