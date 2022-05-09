package com.bogdan.codeforceswatcher.features.users

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.NavigationBar
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class DeleteUserAccountActivity: ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                ContentView(
                    onBack = ::finish,
                    onDeleteAccount = ::startDeleteUserAccountConfirmActivity,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    private fun startDeleteUserAccountConfirmActivity() =
        startActivity(Intent(this, DeleteUserAccountConfirmActivity::class.java))
}

@Composable
private fun ContentView(
    onBack: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    topBar = {
        NavigationBar(title = stringResource(R.string.delete_account)) { onBack() }
    },
    bottomBar = {
        BottomBar(
            onDontDeleteAccount = onBack,
            onDeleteAccount = onDeleteAccount
        )
    },
    backgroundColor = AlgoismeTheme.colors.background
) {
    Content()
}

@Composable
private fun BottomBar(
    onDontDeleteAccount: () -> Unit,
    onDeleteAccount: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    BigButton(stringResource(R.string.do_not_delete)) { onDontDeleteAccount() }

    BigButton(
        label = stringResource(R.string.delete_account),
        isInverted = true
    ) { onDeleteAccount() }
}

@Composable
private fun Content() = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, top = 20.dp, end = 20.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
) {
    Text(
        text = stringResource(R.string.delete_account_warning),
        style = AlgoismeTheme.typography.primarySemiBold,
        color = AlgoismeTheme.colors.error
    )

    Text(
        text = stringResource(R.string.delete_account_explanation),
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary
    )

    Text(
        text = stringResource(R.string.delete_account_hint),
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.error
    )
}

@Composable
private fun Preview() = ContentView(
    onBack = {},
    onDeleteAccount = {}
)