package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class RestorePasswordComposeActivity : ComponentActivity(), StoreSubscriber<AuthState> {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                RestorePasswordScreen()
            }
        }
    }

    private val authState = MutableLiveData<AuthState>()

    private var email = ""

    @ExperimentalComposeUiApi
    @Composable
    private fun RestorePasswordScreen() {
        val localFocusManager = LocalFocusManager.current
        val authState by authState.observeAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NavigationBar { finish() }
            },
            bottomBar = {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData(getString(R.string.lost_access_to_mail), "lost_access_to_mail") { }
                    ),
                    modifier = Modifier
                        .height(62.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .alpha(0f),
                    clickableTextStyle = MaterialTheme.typography.body2.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.secondaryVariant
                    ),
                    paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
                )
            },
            backgroundColor = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(56.dp))

                Title(getString(R.string.restore_password))

                Spacer(Modifier.height(40.dp))

                Text(
                    text = getString(R.string.you_will_get_an_email_with_instructions_for_account_recovery),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    color = MaterialTheme.colors.onBackground,
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.height(36.dp))

                AuthTextField(
                    label = getString(R.string.email),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.clearFocus() }
                    )
                ) { newEmail ->
                    email = newEmail
                }

                Spacer(Modifier.height(24.dp))

                ErrorView(authState?.restorePasswordMessage.orEmpty())

                Spacer(Modifier.height(30.dp))

                AuthButton(getString(R.string.restore_password)) {
                    forgotPassword(email)
                }
            }
            if (authState?.status == AuthState.Status.PENDING) LoadingView()
        }
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
        store.dispatch(AuthRequests.ResetRestorePasswordMessage)
        store.unsubscribe(this)
    }

    private fun forgotPassword(email: String) {
        store.dispatch(AuthRequests.SendPasswordReset(email))
    }

    private fun startRestorePasswordMailSentActivity() {
        startActivity(Intent(this, RestorePasswordMailSentComposeActivity::class.java))
    }

    override fun onNewState(state: AuthState) {
        authState.postValue(state)
        if (state.status == AuthState.Status.DONE) startRestorePasswordMailSentActivity()
        if (state.status == AuthState.Status.IDLE) store.dispatch(AuthRequests.ResetRestorePasswordMessage)
    }
}