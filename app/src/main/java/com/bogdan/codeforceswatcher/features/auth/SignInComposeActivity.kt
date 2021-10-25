package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.google.android.material.button.MaterialButton
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.input_field.view.*
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

    private val AuthState.shouldShowLoading
        get() = status == AuthState.Status.PENDING

    @ExperimentalComposeUiApi
    @Composable
    private fun SignInScreen() {
        val localFocusManager = LocalFocusManager.current

        var email = ""
        var password = ""

        val authState by authState.observeAsState()

        Box {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    NavigationBar { finish() }
                },
                bottomBar = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LinkText(
                            linkTextData = listOf(
                                LinkTextData("Don't have an account yet? "),
                                LinkTextData(
                                    text = "Sign Up",
                                    tag = "sign_up",
                                    annotation = "Redirect_to_sign_up_screen"
                                ) {
                                    startActivity(
                                        Intent(
                                            this@SignInComposeActivity,
                                            SignUpActivity::class.java
                                        )
                                    )
                                }
                            ),
                            modifier = Modifier
                                .height(62.dp)
                                .padding(horizontal = 20.dp)
                                .align(Alignment.TopCenter),
                            textStyle = MaterialTheme.typography.body1.copy(
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.secondaryVariant
                            ),
                            clickableTextStyle = MaterialTheme.typography.body2.copy(
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.secondary
                            )
                        )
                    }

                },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(56.dp))

                    Title("Sign In")

                    Spacer(Modifier.height(44.dp))

                    AuthTextField(
                        label = "Email",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                        )
                    ) { newEmail ->
                        email = newEmail
                    }

                    Spacer(Modifier.height(24.dp))

                    AuthTextField(
                        label = "Password",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { localFocusManager.clearFocus() }
                        ),
                        visualTransformation = PasswordVisualTransformation(mask = '*')
                    ) { newPassword ->
                        password = newPassword
                    }

                    Spacer(Modifier.height(24.dp))

                    ErrorView(authState?.signInMessage.orEmpty())

                    Spacer(Modifier.height(30.dp))

                    AuthButton("SIGN IN") {
                        signInWithEmailAndPassword(email, password)
                    }

                    Spacer(Modifier.height(72.dp))

                    LinkText(
                        linkTextData = listOf(
                            LinkTextData(
                                text = "Forgot password",
                                tag = "forgot_password",
                                annotation = "Redirect_to_forgot_password_screen"
                            ) {
                                forgotPassword(email)
                            }
                        ),
                        modifier = Modifier
                            .height(62.dp)
                            .padding(horizontal = 20.dp),
                        clickableTextStyle = MaterialTheme.typography.body2
                    )
                }
            }
            if (authState?.shouldShowLoading == true) LoadingView()
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

    private fun signInWithEmailAndPassword(email: String, password: String) {
        store.dispatch(AuthRequests.SignIn(email, password))
    }

    private fun forgotPassword(email: String) {
        store.dispatch(AuthRequests.SendPasswordReset(email))
    }

    override fun onNewState(state: AuthState) {
        if (state.status == AuthState.Status.DONE) finish()
        authState.postValue(state)
    }
}