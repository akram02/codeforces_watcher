package com.bogdan.codeforceswatcher.features.users

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
import com.bogdan.codeforceswatcher.features.users.compose.IdentifyView
import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.auth.redux.AuthState

class ProfileItemEpoxyModel(
    private val userAccount: UserAccount?,
    private val authStage: AuthState.Stage
) : BaseEpoxyModel(R.layout.view_compose) {

    init {
        id("ProfileItemEpoxyModel", userAccount.toString(), authStage.toString())
    }

    override fun bind(view: View): Unit = with(view) {
        super.bind(view)

        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            AlgoismeTheme {
                ProfileItem()
            }
        }
    }

    @Composable
    private fun ProfileItem() {
        when (authStage) {
            AuthState.Stage.NOT_SIGNED_IN -> IdentifyView(Modifier.padding(20.dp))
            AuthState.Stage.SIGNED_IN -> /*VerifyView()*/ Text("VerifyView")
            AuthState.Stage.VERIFIED -> /*ProfileView()*/ Text("ProfileView")
        }
    }
}
