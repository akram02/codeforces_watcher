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

    private var email = ""
    private var password = ""

    private val isPending = MutableLiveData(false)
    private val wasLoaderShown = MutableLiveData(false)
    private val isError = MutableLiveData(false)

    @ExperimentalComposeUiApi
    @Composable
    private fun SignInScreen() {
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
                            text = "Don't have an account yet?",
                            textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                            clickableText = "Sign Up",
                            clickableTextStyle = MaterialTheme.typography.body2.copy(fontSize = 14.sp)
                        ) {
                            startActivity(
                                Intent(
                                    this@SignInComposeActivity,
                                    SignUpActivity::class.java
                                )
                            )
                        }
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

                    ErrorView(
                        massage = if (isError == true) "Wrong credentials!" else ""
                    )

                    Spacer(Modifier.height(30.dp))

                    AuthButton("SIGN IN") {
                        signInWithEmailAndPassword(email = email, password = password)
                    }

                    Spacer(Modifier.height(72.dp))

                    AnnotatedClickableText(clickableText = "Forgot password?") {
                        forgotPassword(email = email)
                    }
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

    private fun signInWithEmailAndPassword(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            isError.postValue(true)
        } else {
            store.dispatch(AuthRequests.SignIn(email, password))
        }
    }

    private fun forgotPassword(email: String) {
        if (email.isEmpty()) store.dispatch(AuthRequests.SendPasswordReset.Failure(getString(R.string.forgot_password_empty_email).toMessage()))
        else store.dispatch(AuthRequests.SendPasswordReset(email))
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