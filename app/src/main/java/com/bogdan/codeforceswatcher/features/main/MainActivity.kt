package com.bogdan.codeforceswatcher.features.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.WebViewActivity
import com.bogdan.codeforceswatcher.features.contests.ContestsFragment
import com.bogdan.codeforceswatcher.features.news.NewsFragment
import com.bogdan.codeforceswatcher.features.problems.ProblemsFragment
import com.bogdan.codeforceswatcher.features.users.AddUserBottomSheetFragment
import com.bogdan.codeforceswatcher.features.users.UsersFragment
import com.bogdan.codeforceswatcher.util.FeedbackController
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsActions
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnMenuItemTapListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FeedbackController.feedbackController = FeedbackController(this@MainActivity)
        FeedbackController.get().updateCountOpeningScreen()

        initViews()
    }

    private fun initViews() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.bottom_navigation, NavigationMenuFragment())
            .commit()

        onContestsTabSelected()
    }

    override fun onMenuItemTap(tab: MenuItem.Tab) = when(tab) {
        MenuItem.Tab.CONTESTS -> onContestsTabSelected()
        MenuItem.Tab.USERS -> onUsersTabSelected()
        MenuItem.Tab.NEWS -> onNewsTabSelected()
        MenuItem.Tab.PROBLEMS -> onProblemsTabSelected()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onContestsTabSelected() {
        updateFragment(ContestsFragment())

        fab.setOnClickListener {
            startActivity(
                WebViewActivity.newIntent(
                    this,
                    CONTESTS_LINK,
                    getString(R.string.upcoming_contests)
                )
            )
        }
        fab.setImageDrawable(getDrawable(R.drawable.ic_eye))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onUsersTabSelected() {
        updateFragment(UsersFragment())

        fab.setOnClickListener {
            AddUserBottomSheetFragment().show(supportFragmentManager, null)
        }
        fab.setImageDrawable(getDrawable(R.drawable.ic_plus))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun onNewsTabSelected() {
        updateFragment(NewsFragment())

        fab.setOnClickListener {
            showShareDialog()
            analyticsController.logEvent(AnalyticsEvents.SHARE_APP)
        }
        fab.setImageDrawable(getDrawable(R.drawable.ic_share))
    }

    private fun onProblemsTabSelected() {
        updateFragment(ProblemsFragment())

        var problemsIsFavourite = store.state.problems.isFavourite
        updateProblemsFAB(problemsIsFavourite)

        fab.setOnClickListener {
            problemsIsFavourite = !(problemsIsFavourite)

            store.dispatch(ProblemsActions.ChangeTypeProblems(problemsIsFavourite))
            updateProblemsFAB(problemsIsFavourite)
        }
    }

    private fun updateFragment(fragment: Fragment) =
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun updateProblemsFAB(problemsIsFavourite: Boolean) =
        if (problemsIsFavourite) {
            fab.setImageDrawable(getDrawable(R.drawable.ic_star))
        } else {
            fab.setImageDrawable(getDrawable(R.drawable.ic_all))
        }

    private fun showShareDialog() =
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.share_cw))
            .setMessage(getString(R.string.help_cw_make_more_social))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.share)) { _, _ ->
                share()
                analyticsController.logEvent(AnalyticsEvents.APP_SHARED)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .create()
            .show()

    private fun share() = startActivity(Intent().apply {
        action = Intent.ACTION_SEND
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, getString(R.string.share_cw_message))
    })

    companion object {
        private const val CONTESTS_LINK = "https://clist.by/"
    }
}

data class MenuItem(
    val tab: Tab,
    val iconId: Int,
    val selectedIconId: Int
) {
    enum class Tab { CONTESTS, USERS, NEWS, PROBLEMS }
}

interface OnMenuItemTapListener {

    fun onMenuItemTap(tab: MenuItem.Tab)
}