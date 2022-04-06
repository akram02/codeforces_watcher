package com.bogdan.codeforceswatcher.features.users

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.SpannableString
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.util.CustomMarkerView
import com.bogdan.codeforceswatcher.util.colorSubstring
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.users.models.User
import io.xorum.codeforceswatcher.features.users.redux.UsersRequests
import io.xorum.codeforceswatcher.features.users.redux.UsersState
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.activity_user.*
import tw.geothings.rekotlin.StoreSubscriber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class UserActivity : AppCompatActivity(), StoreSubscriber<UsersState> {

    private val handle
        get() = intent.getStringExtra(HANDLE) ?: throw IllegalArgumentException()

    private val isUserAccount
        get() = intent.getBooleanExtra(IS_USER_ACCOUNT, false)

    private lateinit var user: User

    private var menuItemId by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupChart()

        menuItemId = if (isUserAccount) {
            R.menu.menu_user_activity_log_out
        } else {
            R.menu.menu_user_activity_delete
        }

        store.dispatch(UsersRequests.FetchUser(handle))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun displayUser() {
        tvRank.text = user.buildRank()
        tvUserRating.text = user.buildRating()
        tvUserHandle.text = user.buildHandle()
        tvContribution.text = user.buildContribution()
        (ivUserAvatar as CircleImageView).borderColor =
            ContextCompat.getColor(this, getColorByUserRank(user.rank))

        Picasso.get().load(user.avatar).into(ivUserAvatar)
        title = user.buildFullName()
    }

    private fun User.buildRating() = SpannableString(
        getString(
            R.string.rating,
            rating?.toString() ?: getString(R.string.none),
            maxRating?.toString() ?: getString(R.string.none)
        )
    ).apply {
        rating?.let {
            val startIndex = indexOf(it.toString())
            val color = getColorByUserRank(rank)
            colorSubstring(startIndex, startIndex + it.toString().length, color)
        }

        maxRating?.let {
            val startIndex = lastIndexOf(it.toString())
            val color = getColorByUserRank(maxRank)
            colorSubstring(startIndex, startIndex + it.toString().length, color)
        }
    }

    private fun setupChart() {
        val xAxis = chart.xAxis
        chart.setTouchEnabled(true)
        chart.markerView = CustomMarkerView(this, R.layout.chart)
        chart.isDragEnabled = true
        chart.axisRight.setDrawLabels(false)
        xAxis.setDrawAxisLine(true)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.labelCount = 3

        xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
            val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())
            dateFormat.format(Date(value.toLong() * 1000)).toString()
        }
    }

    private fun displayChart() {
        val entries = user.ratingChanges.map {
            Entry(it.ratingUpdateTimeSeconds.toFloat(), it.newRating.toFloat(), it.toChartItem())
        }

        val lineDataSet = LineDataSet(entries, user.handle)
        lineDataSet.setDrawValues(false)
        chart.data = LineData(lineDataSet)
        chart.invalidate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menuItemId, menu)
        if (isUserAccount) {
            setIconTint(menu, R.id.action_log_out)
        } else {
            setIconTint(menu, R.id.action_delete)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.delete_user))
                    .setMessage(getString(R.string.delete_user_explanation, user.handle))
                    .setPositiveButton(getString(R.string.ok)) { _, _ ->
                        deleteUser()
                    }
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create()
                    .show()
            }
            R.id.action_log_out -> {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.log_out))
                    .setMessage(getString(R.string.do_you_want_to_log_out))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.stay_logged_in), null)
                    .setNegativeButton(getString(R.string.log_out)) { _, _ ->
                        store.dispatch(AuthRequests.LogOut)
                        finish()
                    }
                    .create()
                    .show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setIconTint(menu: Menu, iconId: Int) {
        var icon: Drawable = menu.findItem(iconId).icon
        icon = DrawableCompat.wrap(icon)
        DrawableCompat.setTint(icon, ContextCompat.getColor(this, R.color.secondary))
        menu.findItem(iconId).icon = icon
    }

    private fun deleteUser() {
        store.dispatch(UsersRequests.DeleteUser(user))
    }

    companion object {

        private const val HANDLE = "handle"
        private const val IS_USER_ACCOUNT = "is_user_account"

        fun newIntent(context: Context, handle: String, isUserAccount: Boolean) =
            Intent(context, UserActivity::class.java).apply {
                putExtra(HANDLE, handle)
                putExtra(IS_USER_ACCOUNT, isUserAccount)
            }
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.users == newState.users
            }.select { it.users }
        }
    }

    override fun onStop() {
        super.onStop()
        store.unsubscribe(this)
    }

    override fun onNewState(state: UsersState) {
        if (state.status == UsersState.Status.DONE) finish()
        user = state.currentUser ?: return

        displayUser()

        if (user.ratingChanges.isNotEmpty()) {
            displayChart()
            tvRatingChanges.visibility = View.VISIBLE
        } else {
            tvRatingChanges.visibility = View.INVISIBLE
        }

        spinner.visibility =
            if (state.status == UsersState.Status.PENDING) View.VISIBLE else View.INVISIBLE
    }
}

fun User.buildRank() = rank?.let { colorTextByUserRank(it.capitalize(), it) }
    ?: CwApp.app.applicationContext.getString(R.string.none)

fun User.buildHandle() = colorTextByUserRank(handle, rank)

fun User.buildFullName() = when {
    firstName == null && lastName == null -> handle
    firstName == null -> lastName.orEmpty()
    lastName == null -> firstName.orEmpty()
    else -> "$firstName $lastName"
}

fun User.buildContribution(): SpannableString {
    val contributionString = if (contribution > 0) "+$contribution" else contribution.toString()
    return SpannableString(
        CwApp.app.getString(R.string.contribution, contributionString)
    ).apply {
        val startIndex = indexOf(contributionString)
        val color = if (contribution >= 0) R.color.bright_green else R.color.red
        colorSubstring(startIndex, startIndex + contributionString.length, color)
    }
}

@Composable
fun User.buildRatingNew() = buildAnnotatedString {
    append("${stringResource(R.string.rating_new)}: ")
    withStyle(
        SpanStyle(color = colorResource(getColorByUserRank(rank)))
    ) {
        append(rating.toString())
    }
}

@Composable
fun User.buildMaxRatingNew() = buildAnnotatedString {
    append("${stringResource(R.string.max_rating_new)}: ")
    withStyle(
        SpanStyle(color = colorResource(getColorByUserRank(maxRank)))
    ) {
        append(maxRating.toString())
    }
}

@Composable
fun User.buildContributionNew() = buildAnnotatedString {
    append("${stringResource(R.string.contribution_new)}: ")
    withStyle(
        SpanStyle(
            color = when {
                contribution > 0 -> AlgoismeTheme.colors.green
                contribution < 0 -> AlgoismeTheme.colors.red
                else -> AlgoismeTheme.colors.secondaryVariant
            }
        )
    ) {
        append(
            when {
                contribution > 0 -> "+$contribution"
                contribution < 0 -> "-$contribution"
                else -> contribution.toString()
            }
        )
    }
}

fun User.buildFullNameNew() = when {
    firstName == null && lastName == null -> handle
    firstName == null -> lastName.orEmpty()
    lastName == null -> firstName.orEmpty()
    else -> "$firstName $lastName"
}

@Composable
fun User.buildRankNew() = rank?.let { rank -> rank.replaceFirstChar { it.uppercase() } } ?: CwApp.app.applicationContext.getString(R.string.none)
