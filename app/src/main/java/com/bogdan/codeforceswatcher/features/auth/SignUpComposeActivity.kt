package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.components.compose.PrivacyPolicyChecker
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class SignUpComposeActivity : ComponentActivity(), StoreSubscriber<AuthState> {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                SignUpScreen()
            }
        }
    }

    private val authState = MutableLiveData<AuthState>()

    private var email = ""
    private var password = ""
    private var confirmedPassword = ""

    @ExperimentalComposeUiApi
    @Composable
    private fun SignUpScreen() {
        val localFocusManager = LocalFocusManager.current

        var isPrivacyPolicyAccepted by remember { mutableStateOf(false) }
        val authState by authState.observeAsState()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                NavigationBar { finish() }
            },
            bottomBar = {
                LinkText(
                    linkTextData = listOf(
                        LinkTextData("${getString(R.string.already_have_an_account)} "),
                        LinkTextData(getString(R.string.sign_in)) {
                            startSignInActivity()
                            finish()
                        }
                    ),
                    modifier = Modifier
                        .height(62.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    textStyle = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.secondaryVariant
                    ),
                    clickableTextStyle = MaterialTheme.typography.body2.copy(
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onBackground
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

                Title(getString(R.string.sign_up))

                Spacer(Modifier.height(44.dp))

                AuthTextField(
                    label = getString(R.string.email),
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
                    label = getString(R.string.password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                    ),
                    visualTransformation = PasswordVisualTransformation(mask = '*')
                ) { newPassword ->
                    password = newPassword
                }

                Spacer(Modifier.height(24.dp))

                AuthTextField(
                    label = getString(R.string.confirm_password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { localFocusManager.clearFocus() }
                    ),
                    visualTransformation = PasswordVisualTransformation(mask = '*')
                ) { newConfirmPassword ->
                    confirmedPassword = newConfirmPassword
                }

                Spacer(Modifier.height(36.dp))

                PrivacyPolicyChecker(
                    modifier = Modifier.padding(horizontal = 21.dp),
                    isAccepted = isPrivacyPolicyAccepted,
                    textStyle = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onBackground
                    ),
                ) {
                    isPrivacyPolicyAccepted = !isPrivacyPolicyAccepted
                }

                Spacer(Modifier.height(20.dp))

                ErrorView(authState?.signUpMessage.orEmpty())

                Spacer(Modifier.height(26.dp))

                AuthButton(
                    label = getString(R.string.sign_up).uppercase(),
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = MaterialTheme.colors.secondary,
                        shape = RoundedCornerShape(100)
                    ),
                    isInverted = !isPrivacyPolicyAccepted
                ) {
                    signUp(email, password, confirmedPassword, isPrivacyPolicyAccepted)
                }
            }
        }
        if (authState?.status == AuthState.Status.PENDING) LoadingView()
    }

    private fun signUp(
        email: String,
        password: String,
        confirmedPassword: String,
        isPrivacyPolicyAccepted: Boolean
    ) {
       store.dispatch(AuthRequests.SignUp(email, password, confirmedPassword, isPrivacyPolicyAccepted))
    }

    private fun startSignInActivity() {
        startActivity(
            Intent(this, SignInComposeActivity::class.java)
        )
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.auth.status == newState.auth.status
            }.select { it.auth }
        }
    }

    private fun resetSignUpMessage() = store.dispatch(AuthRequests.ResetSignUpMessage)

    override fun onStop() {
        super.onStop()
        resetSignUpMessage()
        store.unsubscribe(this)
    }

    override fun onNewState(state: AuthState) {
        authState.postValue(state)
        if (state.status == AuthState.Status.DONE) finish()
        if (state.status == AuthState.Status.IDLE) resetSignUpMessage()
    }
}