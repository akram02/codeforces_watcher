package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.textfields.EmailTextField
import com.bogdan.codeforceswatcher.components.compose.textfields.TextFieldPosition
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class RestorePasswordComposeActivity : ComponentActivity(), StoreSubscriber<AuthState> {

    private val authState: MutableState<AuthState?> = mutableStateOf(null)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                RestorePasswordScreen(
                    authState = authState,
                    onBack = ::finish,
                    onForgotPassword = ::onForgotPassword,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun onForgotPassword(email: String) {
        store.dispatch(AuthRequests.SendPasswordReset(email))
    }

    private fun startRestorePasswordMailSentActivity() {
        startActivity(Intent(this, RestorePasswordMailSentComposeActivity::class.java))
    }

    private fun resetRestorePasswordMessage() {
        store.dispatch(AuthRequests.ResetRestorePasswordMessage)
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.auth.status == newState.auth.status
            }.select { it.auth }
        }
    }

    override fun onStop() {
        super.onStop()

        resetRestorePasswordMessage()
        store.unsubscribe(this)
    }

    override fun onNewState(state: AuthState) {
        authState.value = state

        when (state.status) {
            AuthState.Status.DONE -> startRestorePasswordMailSentActivity()
            AuthState.Status.IDLE -> resetRestorePasswordMessage()
            else -> {}
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun RestorePasswordScreen(
    authState: State<AuthState?>,
    onBack: () -> Unit,
    onForgotPassword: (String) -> Unit,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    topBar = { NavigationBar { onBack() } },
    bottomBar = { BottomBar() },
    backgroundColor = AlgoismeTheme.colors.background
) {
    val state = authState.value ?: return@Scaffold

    Content(state, onForgotPassword)
}
@Composable
private fun BottomBar() = LinkText(
    linkTextData = listOf(
        LinkTextData(stringResource(R.string.lost_access_to_mail), "lost_access_to_mail") { }
    ),
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, bottom = 45.dp)
        .alpha(0f),
    clickableTextStyle = AlgoismeTheme.typography.hintSemiBold.copy(
        fontSize = 14.sp,
        color = AlgoismeTheme.colors.secondaryVariant,
        textDecoration = TextDecoration.Underline
    ),
    paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
)

@ExperimentalComposeUiApi
@Composable
private fun Content(
    authState: AuthState,
    onForgotPassword: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }

    if (authState.status == AuthState.Status.PENDING) LoadingView()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        Title(stringResource(R.string.restore_password))

        Spacer(Modifier.height(44.dp))

        Text(
            text = stringResource(R.string.you_will_get_an_email_with_instructions_for_account_recovery),
            modifier = Modifier.fillMaxWidth(),
            style = AlgoismeTheme.typography.hintRegular.copy(fontSize = 14.sp),
            color = AlgoismeTheme.colors.onBackground,
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.height(36.dp))

        EmailTextField(TextFieldPosition.LAST) { email = it }

        Spacer(Modifier.height(24.dp))

        ErrorView(authState.restorePasswordMessage.orEmpty())

        Spacer(Modifier.height(30.dp))

        BigButton(stringResource(R.string.restore_password)) {
            onForgotPassword(email)
        }
    }
}