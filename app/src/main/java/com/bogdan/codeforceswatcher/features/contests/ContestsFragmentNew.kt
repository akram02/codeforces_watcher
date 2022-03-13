package com.bogdan.codeforceswatcher.features.contests

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.features.contests.redux.ContestsState
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber
import java.text.SimpleDateFormat
import java.util.*

class ContestsFragmentNew : Fragment(), StoreSubscriber<ContestsState> {

    private val contestsState: MutableState<ContestsState?> = mutableStateOf(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    contestsState = contestsState,
                    onFilter = { onFilter() }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.contests == newState.contests
            }.select { it.contests }
        }
    }

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this)
    }

    private fun onFilter() {
        startActivity(Intent(activity, ContestsFiltersActivity::class.java))
    }

    override fun onNewState(state: ContestsState) {
        contestsState.value = state
    }
}

@Composable
private fun ContentView(
    contestsState: State<ContestsState?>,
    onFilter: () -> Unit
) = Scaffold(
    topBar = { NavigationBar(onFilter) },
    backgroundColor = AlgoismeTheme.colors.primaryVariant
) {
    val state = contestsState.value ?: return@Scaffold
    val contests = state.contests.filter { it.phase == Contest.Phase.PENDING }
        .sortedBy(Contest::startDateInMillis)
        .filter { state.filters.contains(it.platform) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(AlgoismeTheme.colors.primary)
            .padding(horizontal = 20.dp)
    ) {
        items(contests) {
            ContestView(it)
        }
    }
}

@Composable
private fun NavigationBar(
    onFilter: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 25.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = stringResource(R.string.contests),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.secondary
    )

    Image(
        painter = painterResource(R.drawable.ic_filter_icon),
        contentDescription = null,
        modifier = Modifier.clickable { onFilter() }
    )
}

@Composable
private fun ContestView(
    contest: Contest
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .height(36.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Image(
        painter = painterResource(platformIcon(contest.platform)),
        contentDescription = null
    )

    Spacer(modifier = Modifier.width(8.dp))

    Column(modifier = Modifier.fillMaxWidth(0.9f)) {
        Text(
            text = contest.title,
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = getDateTime(contest.startDateInMillis),
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(y = (-4).dp)
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    Image(
        painter = painterResource(R.drawable.ic_calendar),
        contentDescription = null
    )
}

private fun platformIcon(
    platform: Contest.Platform
) = when (platform) {
    Contest.Platform.ATCODER -> R.drawable.ic_atcoder
    Contest.Platform.TOPCODER -> R.drawable.ic_topcoder
    Contest.Platform.CODEFORCES -> R.drawable.ic_codeforces
    Contest.Platform.CODECHEF -> R.drawable.ic_codechef
    Contest.Platform.CODEFORCES_GYM -> R.drawable.ic_codeforcesgym
    Contest.Platform.LEETCODE -> R.drawable.ic_leetcode
    Contest.Platform.KICK_START -> R.drawable.ic_kickstart
    Contest.Platform.HACKEREARTH -> R.drawable.ic_hackerearth
    Contest.Platform.HACKERRANK -> R.drawable.ic_hackerrank
    Contest.Platform.CS_ACADEMY -> R.drawable.ic_csacademy
    Contest.Platform.TOPH -> R.drawable.ic_toph
}

@Composable
private fun getDateTime(seconds: Long): String {
    val dateFormat =
        SimpleDateFormat(stringResource(R.string.contest_date_format), Locale.getDefault())
    return dateFormat.format(Date(seconds)).toString()
}