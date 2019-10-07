package com.bogdan.codeforceswatcher.redux.states

import com.bogdan.codeforceswatcher.R
import org.rekotlin.StateType

data class UIState(
    val selectedHomeTab: HomeTab = HomeTab.USERS
) : StateType {

    enum class HomeTab {
        USERS,
        CONTESTS,
        ACTIONS,
        PROBLEMS;

        companion object {

            fun fromMenuItemId(menuItemId: Int): HomeTab =
                enumValues<HomeTab>().find { it.menuItemId == menuItemId } ?: USERS
        }

        val titleId: Int
            get() = when (this) {
                USERS -> R.string.empty
                CONTESTS -> R.string.contests
                ACTIONS -> R.string.actions
                PROBLEMS -> R.string.problems
            }

        val menuItemId: Int
            get() = when (this) {
                USERS -> R.id.navUsers
                CONTESTS -> R.id.navContests
                ACTIONS -> R.id.navActions
                PROBLEMS -> R.id.navProblems
            }
    }

}