package com.bogdan.codeforceswatcher.features.users

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.ErrorView
import com.bogdan.codeforceswatcher.components.compose.LoadingView
import com.bogdan.codeforceswatcher.components.compose.NavigationBar
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.main.MainActivity
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class DeleteUserAccountConfirmActivity: ComponentActivity(), StoreSubscriber<AuthState> {

    private val authState: MutableState<AuthState?> = mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                ContentView(
                    authState = authState,
                    onBack = ::finish,
                    onDeleteAccount = ::deleteAccount,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.auth.status == newState.auth.status &&
                        oldState.auth.authStage == newState.auth.authStage
            }.select { it.auth }
        }
    }

    override fun onStop() {
        super.onStop()

        resetDeleteAccountMessage()
        store.unsubscribe(this)
    }

    private fun deleteAccount(isAccepted: Boolean) {
        store.dispatch(AuthRequests.DeleteAccount(isAccepted))
    }

    private fun resetDeleteAccountMessage() =
        store.dispatch(AuthRequests.ResetDeleteAccountMessage)

    override fun onNewState(state: AuthState) {
        authState.value = state

        if (state.authStage == AuthState.Stage.NOT_SIGNED_IN) {
            val mainActivity = Intent(this, MainActivity::class.java)
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(mainActivity)
        }
    }
}

@Composable
private fun ContentView(
    authState: State<AuthState?>,
    onBack: () -> Unit,
    onDeleteAccount: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = authState.value ?: return
    var isDeleteAccountAccepted by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            NavigationBar(
                title = stringResource(R.string.delete_account),
                onClick = onBack
            )
        },
        bottomBar = {
            BottomBar(
                isDeleteAccountAccepted = isDeleteAccountAccepted,
                onCheckboxClick = { isDeleteAccountAccepted = !isDeleteAccountAccepted },
                onDeleteAccount = onDeleteAccount
            )
        },
        backgroundColor = AlgoismeTheme.colors.background
    ) {
        Content(state)
    }
}

@Composable
private fun BottomBar(
    isDeleteAccountAccepted: Boolean,
    onCheckboxClick: () -> Unit,
    onDeleteAccount: (Boolean) -> Unit
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    DeleteAccountChecker(isAccepted = isDeleteAccountAccepted) { onCheckboxClick() }

    BigButton(
        label = stringResource(R.string.delete_account),
        isInverted = !isDeleteAccountAccepted
    ) { onDeleteAccount(isDeleteAccountAccepted) }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Content(authState: AuthState) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
) {
    if (authState.status == AuthState.Status.PENDING) LoadingView()

    Text(
        text = stringResource(R.string.delete_account_warning),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.error
    )

    Text(
        text = stringResource(R.string.delete_account_sure),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.secondary
    )

    Text(
        text = stringResource(R.string.delete_account_hint),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.error
    )

    ErrorView(
        message = authState.deleteAccountMessage.orEmpty(),
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun DeleteAccountChecker(
    isAccepted: Boolean = false,
    onCheckboxClick: () -> Unit
) = Row {
    val checkboxIcon =
        if (isAccepted) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled

    Icon(
        painter = painterResource(id = checkboxIcon),
        contentDescription = "Checkbox",
        modifier = Modifier.clickable { onCheckboxClick() },
        tint = if (isAccepted) AlgoismeTheme.colors.onBackground else AlgoismeTheme.colors.secondaryVariant
    )

    Spacer(Modifier.width(12.dp))

    Text(
        text = stringResource(R.string.want_to_delete_account),
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary
    )
}
