package com.bogdan.codeforceswatcher.features.auth

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
import androidx.compose.ui.platform.LocalFocusManager
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
import io.xorum.codeforceswatcher.redux.toMessage
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

    private var email = ""

    private val isPending = MutableLiveData(false)
    private val wasLoaderShown = MutableLiveData(false)
    private val isError = MutableLiveData(false)

    @ExperimentalComposeUiApi
    @Composable
    private fun RestorePasswordScreen() {
        val localFocusManager = LocalFocusManager.current
        val isPending by isPending.observeAsState()
        val isError by isError.observeAsState()


        Box {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    NavigationBar { finish() }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(62.dp)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        AnnotatedClickableText(
                            clickableText = "Lost access to mail?",
                            clickableTextStyle = MaterialTheme.typography.body2.copy(
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.secondaryVariant
                            )
                        ) { /*TODO*/ }
                    }
                },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(56.dp))

                    Title("Restore Password")

                    Spacer(Modifier.height(40.dp))

                    Text(
                        text = "You will get an email with instructions\n" +
                                "for account recovery",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Start
                    )

                    Spacer(Modifier.height(36.dp))

                    AuthTextField(
                        label = "Email",
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

                    ErrorView(
                        massage = if (isError == true) "User doesn’t exist ¯\\_(ツ)_/¯" else ""
                    )

                    Spacer(Modifier.height(28.dp))

                    AuthButton("Restore Password") { /*TODO*/ }
                }
            }
            if (isPending == true) LoadingView { /*TODO*/ }
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
        store.unsubscribe(this)
    }

    override fun onNewState(state: AuthState) {
        when (state.status) {
            AuthState.Status.PENDING -> {
                isPending.postValue(true)
                wasLoaderShown.postValue(true)
                isError.postValue(false)
            }
            AuthState.Status.DONE -> finish()
            AuthState.Status.IDLE -> {
                isPending.postValue(false)
                if (wasLoaderShown.value == true)
                    isError.postValue(true)
            }
        }
    }
}