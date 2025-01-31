package com.bogdan.codeforceswatcher.features.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
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
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.features.users.models.User
import io.xorum.codeforceswatcher.features.users.redux.FetchUserDataType
import io.xorum.codeforceswatcher.features.users.redux.UsersActions
import io.xorum.codeforceswatcher.features.users.redux.UsersRequests
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.redux.states.AppState
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import tw.geothings.rekotlin.StoreSubscriber

class UsersFragment : Fragment(), StoreSubscriber<AppState> {

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
                    onRefresh = ::onRefresh,
                    onLoginButtonClick = ::startSignInActivity,
                    onVerifyButtonClick = ::startVerifyActivity,
                    onUserClick = ::startUserAccountActivity,
                    onPickerSelected = ::onPicker,
                    modifier = Modifier.fillMaxSize()
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

    private fun onRefresh() {
        store.dispatch(UsersRequests.FetchUserData(FetchUserDataType.REFRESH, isInitiatedByUser = true))
        analyticsController.logEvent(AnalyticsEvents.USERS_REFRESH)
    }

    private fun startSignInActivity() {
        startActivity(Intent(context, SignInComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.SIGN_IN_OPENED)
    }

    private fun startVerifyActivity() {
        startActivity(Intent(context, VerificationComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.VERIFY_OPENED)
    }

    private fun startUserAccountActivity(handle: String, isUserAccount: Boolean) {
        startActivity(context?.let { UserActivity.newIntent(it, handle, isUserAccount) })
    }

    private fun onPicker(position: Int) {
        store.dispatch(UsersActions.Sort(UsersState.SortType.getSortType(position)))
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
    onRefresh: () -> Unit,
    onLoginButtonClick: () -> Unit,
    onVerifyButtonClick: () -> Unit,
    onUserClick: (String, Boolean) -> Unit,
    onPickerSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = appState.value ?: return
    val users = state.users.followedUsers.sort(state.users.sortType).map { UserItem(it) }

    Scaffold(
        topBar = {
            UsersTopBar(
                pickerOptions = R.array.array_sort,
                pickerPosition = state.users.sortType.position,
                pickerCallback = onPickerSelected
            )
        },
        backgroundColor = AlgoismeTheme.colors.primaryVariant
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.users.status == UsersState.Status.PENDING),
            onRefresh = onRefresh,
            modifier = modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(AlgoismeTheme.colors.primary)
        ) {
            LazyColumnContent(
                userAccount = state.users.userAccount,
                authStage = state.auth.authStage,
                users = users,
                onLoginButtonClick = onLoginButtonClick,
                onVerifyButtonClick = onVerifyButtonClick,
                onUserClick = onUserClick
            )
        }
    }
}

@Composable
private fun LazyColumnContent(
    userAccount: UserAccount?,
    authStage: AuthState.Stage,
    users: List<UserItem>,
    onLoginButtonClick: () -> Unit,
    onVerifyButtonClick: () -> Unit,
    onUserClick: (String, Boolean) -> Unit,
) = LazyColumn(contentPadding = PaddingValues(horizontal = 20.dp)) {
    item {
        ProfileItemView(
            userAccount = userAccount,
            authStage = authStage,
            onLoginButtonClick = onLoginButtonClick,
            onVerifyButtonClick = onVerifyButtonClick,
            onViewProfileButtonClick = onUserClick,
        )
    }

    item {
        Text(
            text = stringResource(R.string.users),
            style = AlgoismeTheme.typography.hintSemiBold.copy(fontSize = 20.sp),
            color = AlgoismeTheme.colors.secondary,
            modifier = Modifier.padding(top = 14.dp, bottom = 6.dp)
        )
    }

    items(users) {
        UserItemView(
            userItem = it,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { onUserClick(it.handle.toString(), false) }
        )
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