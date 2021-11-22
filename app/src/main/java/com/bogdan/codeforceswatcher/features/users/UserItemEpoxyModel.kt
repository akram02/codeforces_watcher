package com.bogdan.codeforceswatcher.features.users

import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.UserItemView
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyModel
import kotlinx.android.synthetic.main.view_user_item.view.*

data class UserItemEpoxyModel(
    private val userItem: UserItem
) : BaseEpoxyModel(R.layout.view_user_item_compose) {

    init {
        id("UserItemEpoxyModel", userItem.toString())
    }

    override fun bind(view: View): Unit = with(view) {
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            AlgoismeTheme {
                UserItemView(userItem = userItem)
            }
        }
        super.bind(view)

        setOnClickListener {
            context.startActivity(
                UserActivity.newIntent(
                    context,
                    userItem.handle.toString(),
                    isUserAccount = false
                )
            )
        }
    }

    private fun showLastRatingUpdate(update: Update, view: View) = with(view) {
        when (update) {
            Update.NULL -> {
                ivDelta.setImageResource(0)
                tvLastRatingUpdate.text = null
            }
            Update.DECREASE -> {
                ivDelta.setImageResource(R.drawable.ic_rating_down)
                tvLastRatingUpdate.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            Update.INCREASE -> {
                ivDelta.setImageResource(R.drawable.ic_rating_up)
                tvLastRatingUpdate.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.bright_green
                    )
                )
            }
        }
    }
}
