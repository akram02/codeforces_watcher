package com.bogdan.codeforceswatcher.features.users

import android.view.View
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.features.users.compose.UserItemView
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyModel

data class UserItemEpoxyModel(
    private val userItem: UserItem
) : BaseEpoxyModel(R.layout.view_compose) {

    init {
        id("UserItemEpoxyModel", userItem.toString())
    }

    override fun bind(view: View): Unit = with(view) {
        super.bind(view)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            AlgoismeTheme {
                UserItemView(userItem, Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
            }
        }

        setOnClickListener {
            context.startActivity(
                UserActivity.newIntent(context, userItem.handle.toString(), false)
            )
        }
    }
}
