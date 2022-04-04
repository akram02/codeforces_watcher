package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileItemView(
    userAccount: UserAccount?,
    authStage: AuthState.Stage,
    modifier: Modifier = Modifier,
    onLoginButtonClick: () -> Unit = { },
    onVerifyButtonClick: () -> Unit = { },
    onViewProfileButtonClick: (String, Boolean) -> Unit = { _, _ -> },
) = when (authStage) {
    AuthState.Stage.NOT_SIGNED_IN -> {
        IdentifyView(modifier.padding(top = 20.dp)) { onLoginButtonClick() }
    }
    AuthState.Stage.SIGNED_IN -> {
        VerifyView(modifier.padding(top = 20.dp)) { onVerifyButtonClick() }
    }
    AuthState.Stage.VERIFIED -> {
        Column(modifier.padding(top = 20.dp)) {
            ProfileView(userAccount?.codeforcesUser!!) {
                onViewProfileButtonClick(userAccount.codeforcesUser!!.handle, true)
            }

            Spacer(Modifier.height(6.dp))

            LastUpdateView(buildLastUpdate(userAccount), Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun buildLastUpdate(userAccount: UserAccount?) =
    userAccount?.codeforcesUser?.ratingChanges?.lastOrNull()?.let { ratingChange ->
        stringResource(
            R.string.updated_on,
            SimpleDateFormat(
                stringResource(R.string.user_date_format),
                Locale.getDefault()
            ).format(
                Date(ratingChange.ratingUpdateTimeSeconds * 1000)
            )
        )
    } ?: stringResource(R.string.never_updated)