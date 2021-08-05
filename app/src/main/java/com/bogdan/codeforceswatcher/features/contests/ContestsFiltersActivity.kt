package com.bogdan.codeforceswatcher.features.contests

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.codeforceswatcher.R
import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.features.contests.redux.ContestsRequests
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.activity_filters.*

class ContestsFiltersActivity : AppCompatActivity() {

    private val filtersAdapter: FiltersAdapter = FiltersAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        filtersAdapter.setItems(buildFiltersList())
        recyclerView.adapter = filtersAdapter
    }

    private fun buildFiltersList(): List<FilterItem> {
        val filters = store.state.contests.filters
        return listOf(
                FilterItem(
                        R.drawable.codeforces,
                        "Codeforces",
                        filters.contains(Contest.Platform.CODEFORCES)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.CODEFORCES, isChecked))
                },
                FilterItem(
                        R.drawable.codeforces,
                        "Codeforces Gym",
                        filters.contains(Contest.Platform.CODEFORCES_GYM)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.CODEFORCES_GYM, isChecked))
                },
                FilterItem(
                        R.drawable.atcoder,
                        "AtCoder",
                        filters.contains(Contest.Platform.ATCODER)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.ATCODER, isChecked))
                },
                FilterItem(
                        R.drawable.leetcode,
                        "LeetCode",
                        filters.contains(Contest.Platform.LEETCODE)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.LEETCODE, isChecked))
                },
                FilterItem(
                        R.drawable.topcoder,
                        "TopCoder",
                        filters.contains(Contest.Platform.TOPCODER)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.TOPCODER, isChecked))
                },
                FilterItem(
                        R.drawable.csacademy,
                        "CS Academy",
                        filters.contains(Contest.Platform.CS_ACADEMY)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.CS_ACADEMY, isChecked))
                },
                FilterItem(
                        R.drawable.codechef,
                        "CodeChef",
                        filters.contains(Contest.Platform.CODECHEF)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.CODECHEF, isChecked))
                },
                FilterItem(
                        R.drawable.hackerrank,
                        "HackerRank",
                        filters.contains(Contest.Platform.HACKERRANK)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.HACKERRANK, isChecked))
                },
                FilterItem(
                        R.drawable.hackerearth,
                        "HackerEarth",
                        filters.contains(Contest.Platform.HACKEREARTH)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.HACKEREARTH, isChecked))
                },
                FilterItem(
                        R.drawable.kickstart, "Kick Start",
                        filters.contains(Contest.Platform.KICK_START)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.KICK_START, isChecked))
                },
                FilterItem(
                        R.drawable.toph,
                        "Toph",
                        filters.contains(Contest.Platform.TOPH)
                ) { isChecked ->
                    store.dispatch(ContestsRequests.ChangeFilterCheckStatus(Contest.Platform.TOPH, isChecked))
                }
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
