package com.bogdan.codeforceswatcher.features.users

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyModel
import com.bogdan.codeforceswatcher.features.auth.SignInComposeActivity
import com.bogdan.codeforceswatcher.features.auth.VerificationComposeActivity
import com.bogdan.codeforceswatcher.features.users.compose.ProfileItemView
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import java.text.SimpleDateFormat
import java.util.*

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
                ProfileItemView(
                    userAccount = userAccount,
                    authStage = authStage,
                    lastUpdate = buildLastUpdate(context),
                    onLoginButtonClick = { startSignInActivity(context) },
                    onVerifyButtonClick = { startVerifyActivity(context) },
                    onViewProfileButtonClick = { startUserAccountActivity(context) }
                )
            }
        }
    }

    private fun buildLastUpdate(context: Context) =
        userAccount?.codeforcesUser?.ratingChanges?.lastOrNull()?.let { ratingChange ->
            context.getString(
                R.string.updated_on,
                SimpleDateFormat(
                    context.getString(R.string.user_date_format),
                    Locale.getDefault()
                ).format(
                    Date(ratingChange.ratingUpdateTimeSeconds * 1000)
                )
            )
        } ?: context.getString(R.string.never_updated)

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
