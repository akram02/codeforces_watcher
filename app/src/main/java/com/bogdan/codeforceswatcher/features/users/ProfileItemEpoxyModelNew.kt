package com.bogdan.codeforceswatcher.features.users

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyModel
import com.bogdan.codeforceswatcher.features.auth.SignInComposeActivity
import com.bogdan.codeforceswatcher.features.auth.VerificationComposeActivity
import com.bogdan.codeforceswatcher.features.users.compose.IdentifyView
import com.bogdan.codeforceswatcher.features.users.compose.ProfileView
import com.bogdan.codeforceswatcher.features.users.compose.VerifyView
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.util.AnalyticsEvents

class ProfileItemEpoxyModel(
    private val userAccount: UserAccount?,
    private val authStage: AuthState.Stage,

) : BaseEpoxyModel(R.layout.view_compose) {

    init {
        id("ProfileItemEpoxyModel", userAccount.toString(), authStage.toString())
    }

    override fun bind(view: View): Unit = with(view) {
        super.bind(view)

        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            AlgoismeTheme {
                ProfileItem(context)
            }
        }
    }

    @Composable
    private fun ProfileItem(context: Context) {
        when (authStage) {
            AuthState.Stage.NOT_SIGNED_IN -> {
                IdentifyView(Modifier.padding(20.dp)) { startSignInActivity(context) }
            }
            AuthState.Stage.SIGNED_IN -> {
                VerifyView(Modifier.padding(20.dp)) { startVerifyActivity(context) }
            }
            AuthState.Stage.VERIFIED -> {
                ProfileView(userAccount?.codeforcesUser!!, Modifier.padding(20.dp)) {
                    startUserAccountActivity(context)
                }
            }
        }
    }

    private fun startSignInActivity(context: Context) {
        context.startActivity(Intent(context, SignInComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.SIGN_IN_OPENED)
    }

    private fun startVerifyActivity(context: Context) {
        context.startActivity(Intent(context, VerificationComposeActivity::class.java))
        analyticsController.logEvent(AnalyticsEvents.VERIFY_OPENED)
    }

    private fun startUserAccountActivity(context: Context) {
        context.startActivity(
            UserActivity.newIntent(context, userAccount?.codeforcesUser?.handle!!, true)
        )
    }
}
