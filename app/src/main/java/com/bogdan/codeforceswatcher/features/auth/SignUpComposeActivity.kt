package com.bogdan.codeforceswatcher.features.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.WebViewActivity
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.textfields.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.Constants.PRIVACY_POLICY_LINK
import io.xorum.codeforceswatcher.util.Constants.TERMS_AND_CONDITIONS_LINK
import tw.geothings.rekotlin.StoreSubscriber

class SignUpComposeActivity : ComponentActivity(), StoreSubscriber<AuthState> {

    private val authState: MutableState<AuthState?> = mutableStateOf(null)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                SignUpScreen(
                    authState = authState,
                    onBack = ::finish,
                    startSignInActivity = ::startSignInActivity,
                    onSignUp = ::onSignUp,
                    onTermsAndConditions = ::onTermsAndConditions,
                    onPrivacyPolicy = ::onPrivacyPolicy,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun onSignUp(
        email: String,
        password: String,
        confirmedPassword: String,
        isPrivacyPolicyAccepted: Boolean
    ) = store.dispatch(
        AuthRequests.SignUp(email, password, confirmedPassword, isPrivacyPolicyAccepted)
    )

    private fun onTermsAndConditions() =
        startActivity(
            WebViewActivity.newIntent(
                this,
                TERMS_AND_CONDITIONS_LINK,
                getString(R.string.terms_and_conditions)
            )
        )

    private fun onPrivacyPolicy() =
        startActivity(
            WebViewActivity.newIntent(
                this,
                PRIVACY_POLICY_LINK,
                getString(R.string.privacy_policy)
            )
        )

    private fun startSignInActivity() {
        val intent = Intent(this, SignInComposeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }

    private fun resetSignUpMessage() = store.dispatch(AuthRequests.ResetSignUpMessage)

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

        resetSignUpMessage()
        store.unsubscribe(this)
    }

    override fun onNewState(state: AuthState) {
        authState.value = state

        when (state.status) {
            AuthState.Status.DONE -> finish()
            AuthState.Status.IDLE -> resetSignUpMessage()
            else -> {}
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun SignUpScreen(
    authState: State<AuthState?>,
    onBack: () -> Unit,
    startSignInActivity: () -> Unit,
    onSignUp: (String, String, String, Boolean) -> Unit,
    onTermsAndConditions: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    topBar = { NavigationBar { onBack() } },
    bottomBar = { BottomBar(startSignInActivity) },
    backgroundColor = AlgoismeTheme.colors.background
) {
    val state = authState.value ?: return@Scaffold

    Content(state, onSignUp, onTermsAndConditions, onPrivacyPolicy)
}

@Composable
private fun BottomBar(startSignInActivity: () -> Unit) = LinkText(
    linkTextData = listOf(
        LinkTextData("${stringResource(R.string.already_have_an_account)} "),
        LinkTextData(stringResource(R.string.sign_in), "sign_in") { startSignInActivity() }
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
    onSignUp: (String, String, String, Boolean) -> Unit,
    onTermsAndConditions: () -> Unit,
    onPrivacyPolicy: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPrivacyPolicyAccepted by remember { mutableStateOf(false) }

    if (authState.status == AuthState.Status.PENDING) LoadingView()

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(56.dp))

        Title(stringResource(R.string.sign_up))

        Spacer(Modifier.height(44.dp))

        EmailTextField { email = it }

        Spacer(Modifier.height(24.dp))

        PasswordTextField { password = it }

        Spacer(Modifier.height(24.dp))

        PasswordTextField(
            label = stringResource(R.string.confirm_password),
            position = TextFieldPosition.LAST
        ) {
            confirmPassword = it
        }

        Spacer(Modifier.height(36.dp))

        PrivacyPolicyChecker(
            modifier = Modifier.padding(horizontal = 21.dp),
            isAccepted = isPrivacyPolicyAccepted,
            textStyle = AlgoismeTheme.typography.hintRegular.copy(
                color = AlgoismeTheme.colors.onBackground
            ),
            onTermsAndConditions = onTermsAndConditions,
            onPrivacyPolicy = onPrivacyPolicy
        ) {
            isPrivacyPolicyAccepted = !isPrivacyPolicyAccepted
        }

        Spacer(Modifier.height(20.dp))

        ErrorView(authState.signUpMessage.orEmpty())

        Spacer(Modifier.height(26.dp))

        BigButton(
            label = stringResource(R.string.sign_up).uppercase(),
            modifier = Modifier.border(
                width = 2.dp,
                color = AlgoismeTheme.colors.secondary,
                shape = RoundedCornerShape(100)
            ),
            isInverted = !isPrivacyPolicyAccepted
        ) {
            onSignUp(email, password, confirmPassword, isPrivacyPolicyAccepted)
        }
    }
}

@Composable
private fun PrivacyPolicyChecker(
    modifier: Modifier = Modifier,
    isAccepted: Boolean = false,
    textStyle: TextStyle = AlgoismeTheme.typography.hintRegular,
    clickableTextStyle: TextStyle = AlgoismeTheme.typography.hintSemiBold,
    paragraphStyle: ParagraphStyle = ParagraphStyle(),
    onTermsAndConditions: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onCheckboxClick: () -> Unit
) = Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
) {
    val checkboxIcon =
        if (isAccepted) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled

    Icon(
        painter = painterResource(id = checkboxIcon),
        contentDescription = "Checkbox",
        modifier = Modifier.clickable { onCheckboxClick() },
        tint = if (isAccepted) AlgoismeTheme.colors.onBackground else AlgoismeTheme.colors.secondaryVariant
    )

    Spacer(Modifier.width(12.dp))

    LinkText(
        linkTextData = listOf(
            LinkTextData("${stringResource(R.string.agree_with_the_conditions_and_privacy_policy_start)} "),
            LinkTextData(
                text = stringResource(R.string.terms_and_conditions),
                tag = "terms_and_conditions",
                annotation = TERMS_AND_CONDITIONS_LINK
            ) {
                onTermsAndConditions()
            },
            LinkTextData(" ${stringResource(R.string.agree_with_the_conditions_and_privacy_policy_end)} "),
            LinkTextData(
                text = stringResource(R.string.privacy_policy),
                tag = "privacy_policy",
                annotation = PRIVACY_POLICY_LINK,
            ) {
                onPrivacyPolicy()
            },
        ),
        textStyle = textStyle,
        clickableTextStyle = clickableTextStyle,
        paragraphStyle = paragraphStyle
    )
}