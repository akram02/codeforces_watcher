package com.bogdan.codeforceswatcher.features.auth

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.textfields.HandleTextField
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.verification.redux.VerificationRequests
import io.xorum.codeforceswatcher.features.verification.redux.VerificationState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class VerificationComposeActivity : ComponentActivity(), StoreSubscriber<VerificationState> {

    private val verificationState: MutableState<VerificationState?> = mutableStateOf(null)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                VerificationScreen(
                    verificationState = verificationState,
                    onBack = ::finish,
                    copyText = ::copyText,
                    showToast = ::showToast,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun copyText(text: String) {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("code", text)
        clipboard.setPrimaryClip(clip)
    }

    private fun showToast(id: Int) =
        Toast.makeText(CwApp.app, getString(id), Toast.LENGTH_SHORT).show()

    private fun fetchVerificationCode() =
        store.dispatch(VerificationRequests.FetchVerificationCode())

    private fun resetVerificationCodeforcesMessage() =
        store.dispatch(VerificationRequests.ResetVerificationCodeforcesMessage)

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.verification.status == newState.verification.status
            }.select { it.verification }
        }
        fetchVerificationCode()
    }

    override fun onStop() {
        super.onStop()

        resetVerificationCodeforcesMessage()
        store.unsubscribe(this)
    }

    override fun onNewState(state: VerificationState) {
        verificationState.value = state
        when (state.status) {
            VerificationState.Status.DONE -> finish()
            VerificationState.Status.IDLE -> resetVerificationCodeforcesMessage()
            else -> {}
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun VerificationScreen(
    verificationState: State<VerificationState?>,
    onBack: () -> Unit,
    copyText: (String) -> Unit,
    showToast: (Int) -> Unit,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    topBar = { NavigationBar { onBack() } },
    backgroundColor = AlgoismeTheme.colors.background
) {
    val state = verificationState.value ?: return@Scaffold

    Content(state, copyText, showToast)
}

@ExperimentalComposeUiApi
@Composable
private fun Content(
    verificationState: VerificationState,
    copyText: (String) -> Unit,
    showToast: (Int) -> Unit
) {
    var handle by remember { mutableStateOf("") }

    if (verificationState.status == VerificationState.Status.PENDING) LoadingView()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        Title(stringResource(R.string.verify_codeforces_account))

        Spacer(Modifier.height(44.dp))

        HandleTextField { handle = it }

        Spacer(Modifier.height(24.dp))

        VerificationInstructions()

        Spacer(Modifier.height(12.dp))

        VerificationCode(
            verificationCode = verificationState.verificationCode,
            copyText = copyText,
            showToast = showToast
        )

        Spacer(Modifier.height(20.dp))

        VerificationHint()

        Spacer(Modifier.height(24.dp))

        ErrorView(verificationState.message.orEmpty())

        Spacer(Modifier.height(30.dp))

        BigButton(stringResource(R.string.verify).uppercase()) {
            store.dispatch(VerificationRequests.VerifyCodeforces(handle))
        }
    }
}

@Composable
private fun VerificationInstructions() = Text(
    text = buildAnnotatedString {
        append(stringResource(R.string.to_verify_please_change_your_last_name_start) + "\n")
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = AlgoismeTheme.colors.onBackground,
                letterSpacing = (-1).sp
            )
        ) {
            append(stringResource(R.string.to_verify_please_change_your_last_name_path) + "\n")
        }
        append(stringResource(R.string.to_verify_please_change_your_last_name_end))
    },
    modifier = Modifier.fillMaxWidth(),
    style = AlgoismeTheme.typography.hintRegular.copy(fontSize = 14.sp),
    color = AlgoismeTheme.colors.secondaryVariant,
    textAlign = TextAlign.Start,
    letterSpacing = (-1.5).sp
)

@Composable
private fun VerificationCode(
    verificationCode: String?,
    copyText: (String) -> Unit,
    showToast: (Int) -> Unit
) = ClickableText(
    text = buildAnnotatedString {
        append(verificationCode.orEmpty())
    },
    style = AlgoismeTheme.typography.hintRegular.copy(
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        color = AlgoismeTheme.colors.onBackground,
        textAlign = TextAlign.Center
    ),
    onClick = {
        copyText(verificationCode.orEmpty())
        showToast(R.string.code_copied_to_clipboard)
    }
)

@Composable
private fun VerificationHint() = Text(
    text = stringResource(R.string.after_successful_login_you_can_change_it_back),
    modifier = Modifier.fillMaxWidth(),
    style = AlgoismeTheme.typography.hintRegular.copy(fontSize = 14.sp),
    color = AlgoismeTheme.colors.secondaryVariant,
    textAlign = TextAlign.Start,
    letterSpacing = (-1.5).sp
)