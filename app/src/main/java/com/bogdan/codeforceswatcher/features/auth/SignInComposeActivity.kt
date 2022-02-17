package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
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

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                SignInScreen()
            }
        }
    }

    private val authState = MutableLiveData<AuthState>()

    private var email = ""
    private var password = ""

    @ExperimentalComposeUiApi
    @Composable
    private fun SignInScreen() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            backgroundColor = AlgoismeTheme.colors.background
        ) {
            Content()
        }
    }

    @Composable
    private fun TopBar() { NavigationBar { finish() } }

    @Composable
    private fun BottomBar() {
        LinkText(
            linkTextData = listOf(
                LinkTextData(("${getString(R.string.dont_have_an_account_yet)} ")),
                LinkTextData(getString(R.string.sign_up), "sign_up") { startSignUpActivity() }
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
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun Content() {
        val authState by authState.observeAsState()

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(56.dp))

            Title(getString(R.string.sign_in))

            Spacer(Modifier.height(44.dp))

            EmailTextField { email = it }

            Spacer(Modifier.height(24.dp))

            PasswordTextField(position = TextFieldPosition.LAST) { password = it }

            Spacer(Modifier.height(24.dp))

            ErrorView(authState?.signInMessage.orEmpty())

            Spacer(Modifier.height(30.dp))

            BigButton(getString(R.string.sign_in).uppercase()) {
                signInWithEmailAndPassword(email, password)
            }

            Spacer(Modifier.height(72.dp))

            LinkText(
                linkTextData = listOf(
                    LinkTextData(getString(R.string.forgot_password), "forgot_password") {
                        startRestorePasswordActivity()
                    }
                ),
                clickableTextStyle = AlgoismeTheme.typography.hintSemiBold.copy(
                    color = AlgoismeTheme.colors.onBackground
                )
            )
        }
        if (authState?.status == AuthState.Status.PENDING) LoadingView()
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
        authState.postValue(state)
        when (state.status) {
            AuthState.Status.DONE -> finish()
            AuthState.Status.IDLE -> resetSignInMessage()
            else -> return
        }
    }
}