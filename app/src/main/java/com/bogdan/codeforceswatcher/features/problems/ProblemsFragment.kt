package com.bogdan.codeforceswatcher.features.problems

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.features.problems.redux.requests.ProblemsRequests
import com.bogdan.codeforceswatcher.features.problems.redux.states.ProblemsState
import com.bogdan.codeforceswatcher.store
import com.bogdan.codeforceswatcher.util.Analytics
import com.bogdan.codeforceswatcher.util.Refresh
import kotlinx.android.synthetic.main.fragment_problems.*
import org.rekotlin.StoreSubscriber

class ProblemsFragment : Fragment(), StoreSubscriber<ProblemsState>, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var problemsAdapter: ProblemsAdapter
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_problems, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_search, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as? SearchView

        searchView?.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                problemsAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initViews() {
        swipeRefreshLayout.setOnRefreshListener(this)
        problemsAdapter = ProblemsAdapter(requireContext()) {
            startActivity(ProblemActivity.newIntent(requireContext(), it))
        }
        recyclerView.adapter = problemsAdapter
    }

    override fun onRefresh() {
        store.dispatch(ProblemsRequests.FetchProblems(true))
        Analytics.logRefreshingData(Refresh.ACTIONS)
    }

    override fun onStart() {
        super.onStart()
        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.problems == newState.problems
            }.select { it.problems }
        }
    }

    override fun onStop() {
        super.onStop()
        store.unsubscribe(this)
    }

    override fun newState(state: ProblemsState) {
        swipeRefreshLayout.isRefreshing = (state.status == ProblemsState.Status.PENDING)
        problemsAdapter.setItems(
            if (state.isFavourite) state.problems.filter { it.isFavourite }.sortedByDescending { it.contestTime }
            else state.problems.sortedByDescending { it.contestTime },
            searchView?.query?.toString().orEmpty()
        )
    }
}