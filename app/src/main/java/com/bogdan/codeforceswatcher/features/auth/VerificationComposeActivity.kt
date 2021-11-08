package com.bogdan.codeforceswatcher.features.auth

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.verification.redux.VerificationRequests
import io.xorum.codeforceswatcher.features.verification.redux.VerificationState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class VerificationComposeActivity : ComponentActivity(), StoreSubscriber<VerificationState> {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                VerificationScreen()
            }
        }
    }

    private val verificationState = MutableLiveData<VerificationState>()

    @ExperimentalComposeUiApi
    @Composable
    private fun VerificationScreen() {
        val localFocusManager = LocalFocusManager.current
        val verificationState by verificationState.observeAsState()
        var handle = ""

        fetchVerificationCode()

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

                Title(getString(R.string.verify_codeforces_account))

                Spacer(Modifier.height(40.dp))

                AuthTextField(
                    label = getString(R.string.codeforces_handle),
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
                        append(getString(R.string.to_verify_please_change_your_last_name_start))
                        withStyle(
                            SpanStyle(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onBackground,
                                letterSpacing = (-1).sp
                            )
                        ) {
                            append(getString(R.string.to_verify_please_change_your_last_name_path))
                        }
                        append(getString(R.string.to_verify_please_change_your_last_name_end))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    color = MaterialTheme.colors.secondaryVariant,
                    textAlign = TextAlign.Start,
                    letterSpacing = (-1.5).sp
                )

                Spacer(Modifier.height(12.dp))

                ClickableText(
                    text = buildAnnotatedString {
                        append(verificationState?.verificationCode.orEmpty())
                    },
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onBackground,
                        textAlign = TextAlign.Center
                    ),
                    onClick = {
                        copyText(verificationState?.verificationCode.orEmpty())
                        showToast(getString(R.string.code_copied_to_clipboard))
                    }
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = getString(R.string.after_successful_login_you_can_change_it_back),
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    color = MaterialTheme.colors.secondaryVariant,
                    textAlign = TextAlign.Start,
                    letterSpacing = (-1.5).sp
                )

                Spacer(Modifier.height(24.dp))

                ErrorView(verificationState?.message.orEmpty())

                Spacer(Modifier.height(30.dp))

                AuthButton(getString(R.string.verify).uppercase()) {
                    store.dispatch(VerificationRequests.VerifyCodeforces(handle))
                }
            }
            if (verificationState?.status == VerificationState.Status.PENDING) LoadingView()
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
        if (state.status == VerificationState.Status.DONE) finish()
        if (state.status == VerificationState.Status.IDLE) resetVerificationCodeforcesMessage()
    }

    private fun copyText(text: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("code", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showToast(message: String) =
        Toast.makeText(CwApp.app, message, Toast.LENGTH_SHORT).show()

    private fun resetVerificationCodeforcesMessage() {
        store.dispatch(VerificationRequests.ResetVerificationCodeforcesMessage)
    }

    private fun fetchVerificationCode() {
        store.dispatch(VerificationRequests.FetchVerificationCode())
    }
}