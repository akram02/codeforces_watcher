package com.bogdan.codeforceswatcher.features.users

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
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
                ProfileItem(context)
            }
        }
    }

    @Composable
    private fun ProfileItem(context: Context, modifier: Modifier = Modifier) {
        when (authStage) {
            AuthState.Stage.NOT_SIGNED_IN -> {
                IdentifyView(modifier.padding(20.dp)) { startSignInActivity(context) }
            }
            AuthState.Stage.SIGNED_IN -> {
                VerifyView(modifier.padding(20.dp)) { startVerifyActivity(context) }
            }
            AuthState.Stage.VERIFIED -> {
                Column(modifier.padding(20.dp)) {
                    ProfileView(userAccount?.codeforcesUser!!) { startUserAccountActivity(context) }

                    Spacer(Modifier.height(6.dp))

                    LastUpdate(context, Modifier.fillMaxWidth())
                }
            }
        }
    }

    @Composable
    private fun LastUpdate(context: Context, modifier: Modifier = Modifier) {
        Text(
            text = buildLastUpdate(context),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.secondaryVariant,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
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
