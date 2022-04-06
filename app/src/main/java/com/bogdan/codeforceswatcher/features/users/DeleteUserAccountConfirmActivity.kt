package com.bogdan.codeforceswatcher.features.users

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.NavigationBar
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class DeleteUserAccountConfirmActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                ContentView(
                    onBack = ::finish,
                    onDeleteAccount = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun ContentView(
    onBack: () -> Unit,
    onDeleteAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isDeleteAccountAccepted by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            NavigationBar(
                title = stringResource(R.string.delete_account),
                onClick = onBack
            )
        },
        bottomBar = {
            BottomBar(
                isDeleteAccountAccepted = isDeleteAccountAccepted,
                onCheckboxClick = { isDeleteAccountAccepted = !isDeleteAccountAccepted },
                onDeleteAccount = onDeleteAccount
            )
        },
        backgroundColor = AlgoismeTheme.colors.background
    ) {
        Content()
    }
}

@Composable
private fun BottomBar(
    isDeleteAccountAccepted: Boolean,
    onCheckboxClick: () -> Unit,
    onDeleteAccount: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 40.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    DeleteAccountChecker(isAccepted = isDeleteAccountAccepted) { onCheckboxClick() }

    BigButton(
        label = stringResource(R.string.delete_account),
        isInverted = !isDeleteAccountAccepted
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
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.error
    )

    Text(
        text = stringResource(R.string.delete_account_sure),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.secondary
    )

    Text(
        text = stringResource(R.string.delete_account_hint),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.error
    )
}

@Composable
private fun DeleteAccountChecker(
    isAccepted: Boolean = false,
    onCheckboxClick: () -> Unit
) = Row {
    val checkboxIcon =
        if (isAccepted) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled

    Icon(
        painter = painterResource(id = checkboxIcon),
        contentDescription = "Checkbox",
        modifier = Modifier.clickable { onCheckboxClick() },
        tint = if (isAccepted) AlgoismeTheme.colors.onBackground else AlgoismeTheme.colors.secondaryVariant
    )

    Spacer(Modifier.width(12.dp))

    Text(
        text = stringResource(R.string.want_to_delete_account),
        style = AlgoismeTheme.typography.hintRegular,
        color = AlgoismeTheme.colors.secondary
    )
}
