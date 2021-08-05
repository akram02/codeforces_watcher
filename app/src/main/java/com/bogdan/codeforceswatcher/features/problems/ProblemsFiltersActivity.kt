package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.features.contests.FilterItem
import com.bogdan.codeforceswatcher.features.contests.FiltersAdapter
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsRequests
import io.xorum.codeforceswatcher.features.problems.redux.ProblemsState
import io.xorum.codeforceswatcher.redux.store
import kotlinx.android.synthetic.main.activity_filters.*
import tw.geothings.rekotlin.StoreSubscriber

class ProblemsFiltersActivity : AppCompatActivity(), StoreSubscriber<ProblemsState> {

    private val filtersAdapter by lazy { FiltersAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        recyclerView.adapter = filtersAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onNewState(state: ProblemsState) {
        filtersAdapter.setItems(state.tags.toFilterItems(state))
    }

    private fun List<String>.toFilterItems(state: ProblemsState) = map { tag ->
        FilterItem(
            title = tag,
            imageId = null,
            isChecked = state.selectedTags.contains(tag),
        ) { isChecked ->
            store.dispatch(ProblemsRequests.ChangeTagCheckStatus(tag, isChecked))
        }
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.problems.tags == newState.problems.tags
                        && oldState.problems.selectedTags == newState.problems.selectedTags
            }.select { it.problems }
        }
    }

    override fun onStop() {
        super.onStop()
        store.unsubscribe(this)
    }
}