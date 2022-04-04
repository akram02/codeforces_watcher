package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import com.bogdan.codeforceswatcher.components.compose.textfields.PasswordTextField
import com.bogdan.codeforceswatcher.components.compose.textfields.TextFieldPosition
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class SignInComposeActivity : ComponentActivity(), StoreSubscriber<AuthState> {

    private val authState: MutableState<AuthState?> = mutableStateOf(null)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                SignInScreen(
                    authState = authState,
                    onBack = { finish() },
                    startSignUpActivity = ::startSignUpActivity,
                    onSignIn = ::signInWithEmailAndPassword,
                    startRestorePasswordActivity = ::startRestorePasswordActivity
                )
            }
        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {
        store.dispatch(AuthRequests.SignIn(email, password))
    }

    private fun startSignUpActivity() {
        startActivity(Intent(this, SignUpComposeActivity::class.java))
    }

    private fun startRestorePasswordActivity() {
        startActivity(Intent(this, RestorePasswordComposeActivity::class.java))
    }

    private fun resetSignInMessage() {
        store.dispatch(AuthRequests.ResetSignInMessage)
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

        resetSignInMessage()
        store.unsubscribe(this)
    }

    override fun onNewState(state: AuthState) {
        authState.value = state
        when (state.status) {
            AuthState.Status.DONE -> finish()
            AuthState.Status.IDLE -> resetSignInMessage()
            else -> {}
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun SignInScreen(
    authState: State<AuthState?>,
    onBack: () -> Unit,
    startSignUpActivity: () -> Unit,
    onSignIn: (String, String) -> Unit,
    startRestorePasswordActivity: () -> Unit
) = Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = { TopBar(onBack) },
    bottomBar = { BottomBar(startSignUpActivity) },
    backgroundColor = AlgoismeTheme.colors.background
) {
    val state = authState.value ?: return@Scaffold

    Content(state, onSignIn, startRestorePasswordActivity)
}

@Composable
private fun TopBar(onBack: () -> Unit) = NavigationBar { onBack() }

@Composable
private fun BottomBar(startSignUpActivity: () -> Unit) = LinkText(
    linkTextData = listOf(
        LinkTextData(("${stringResource(R.string.dont_have_an_account_yet)} ")),
        LinkTextData(stringResource(R.string.sign_up), "sign_up") { startSignUpActivity() }
    ),
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, bottom = 45.dp),
    textStyle = AlgoismeTheme.typography.hintRegular.copy(
        fontSize = 14.sp,
        color = AlgoismeTheme.colors.secondaryVariant
    ),
    clickableTextStyle = AlgoismeTheme.typography.hintSemiBold.copy(
        fontSize = 14.sp,
        color = AlgoismeTheme.colors.onBackground,
        textDecoration = TextDecoration.Underline
    ),
    paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
)

@ExperimentalComposeUiApi
@Composable
private fun Content(
    authState: AuthState,
    onSignIn: (String, String) -> Unit,
    startRestorePasswordActivity: () -> Unit
) {
    var email = ""
    var password = ""

    if (authState.status == AuthState.Status.PENDING) LoadingView()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        Title(stringResource(R.string.sign_in))

        Spacer(Modifier.height(44.dp))

        EmailTextField { email = it }

        Spacer(Modifier.height(24.dp))

        PasswordTextField(position = TextFieldPosition.LAST) { password = it }

        Spacer(Modifier.height(24.dp))

        ErrorView(authState.signInMessage.orEmpty())

        Spacer(Modifier.height(30.dp))

        BigButton(stringResource(R.string.sign_in).uppercase()) {
            onSignIn(email, password)
        }

        Spacer(Modifier.height(72.dp))

        LinkText(
            linkTextData = listOf(
                LinkTextData(stringResource(R.string.forgot_password), "forgot_password") {
                    startRestorePasswordActivity()
                }
            ),
            clickableTextStyle = AlgoismeTheme.typography.hintSemiBold.copy(
                color = AlgoismeTheme.colors.onBackground
            )
        )
    }
}