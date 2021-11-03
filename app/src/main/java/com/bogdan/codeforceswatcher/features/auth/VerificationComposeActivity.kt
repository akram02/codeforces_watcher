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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.features.verification.redux.VerificationRequests
import io.xorum.codeforceswatcher.features.verification.redux.VerificationState
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.activity_verification.*
import kotlinx.android.synthetic.main.input_field.view.*
import tw.geothings.rekotlin.StoreSubscriber

class VerificationComposeActivity : ComponentActivity(), StoreSubscriber<VerificationState> {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                RestorePasswordScreen()
            }
        }
    }

    private val verificationState = MutableLiveData<VerificationState>()

    @ExperimentalComposeUiApi
    @Composable
    private fun RestorePasswordScreen() {
        val localFocusManager = LocalFocusManager.current

        var handle = ""

        val verificationState by verificationState.observeAsState()

        Box {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    NavigationBar { finish() }
                },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(56.dp))

                    Title("Verify Codeforces Account")

                    Spacer(Modifier.height(40.dp))

                    AuthTextField(
                        label = "Codeforces handle",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { localFocusManager.clearFocus() }
                        )
                    ) { newHandle ->
                        handle = newHandle
                    }

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = buildAnnotatedString {
                            append(
                                "To verify that account belongs to you, please, " +
                                        "change your English ”Last name” in your\n"
                            )
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colors.onBackground,
                                    letterSpacing = (-1).sp
                                )
                            ) {
                                append("Profile -> Settings -> Social\n")
                            }
                            append("to this value:")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                        color = MaterialTheme.colors.secondaryVariant,
                        textAlign = TextAlign.Start,
                        letterSpacing = (-1.5).sp
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "xV123GH5",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = "After successful login you can change it back.",
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                        color = MaterialTheme.colors.secondaryVariant,
                        textAlign = TextAlign.Start,
                        letterSpacing = (-1.5).sp
                    )

                    Spacer(Modifier.height(24.dp))

                    ErrorView(getString(R.string.wrong_credentials))

                    Spacer(Modifier.height(30.dp))

                    AuthButton("VERIFY") {
                        store.dispatch(VerificationRequests.VerifyCodeforces(handle))
                    }
                }
                if (verificationState?.status == VerificationState.Status.PENDING) LoadingView()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.verification.status == newState.verification.status
            }.select { it.verification }
        }
    }

    override fun onStop() {
        super.onStop()
        store.dispatch(VerificationRequests.ResetVerificationCodeforcesMessage)
        store.unsubscribe(this)
    }

    override fun onNewState(state: VerificationState) {
        verificationState.postValue(state)
    }

}