package com.bogdan.codeforceswatcher.features.users.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.auth.redux.AuthState

@Composable
fun ProfileItemView(
    userAccount: UserAccount?,
    authStage: AuthState.Stage,
    lastUpdate: String,
    modifier: Modifier = Modifier,
    onLoginButtonClick: () -> Unit = { },
    onVerifyButtonClick: () -> Unit = { },
    onViewProfileButtonClick: () -> Unit = { },
) {
    when (authStage) {
        AuthState.Stage.NOT_SIGNED_IN -> {
            IdentifyView(modifier.padding(20.dp)) { onLoginButtonClick() }
        }
        AuthState.Stage.SIGNED_IN -> {
            VerifyView(modifier.padding(20.dp)) { onVerifyButtonClick() }
        }
        AuthState.Stage.VERIFIED -> {
            Column(modifier.padding(20.dp)) {
                ProfileView(userAccount?.codeforcesUser!!) { onViewProfileButtonClick() }

                Spacer(Modifier.height(6.dp))

                LastUpdateView(lastUpdate, Modifier.fillMaxWidth())
            }
        }
    }
}