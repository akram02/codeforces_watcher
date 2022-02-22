package com.bogdan.codeforceswatcher.features.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.components.WebViewActivity
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.cells.PostView
import com.bogdan.codeforceswatcher.features.news.cells.PostWithCommentView
import io.xorum.codeforceswatcher.features.news.models.News
import io.xorum.codeforceswatcher.features.news.redux.NewsState
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import tw.geothings.rekotlin.StoreSubscriber

class NewsFragmentNew : Fragment(), StoreSubscriber<NewsState> {

    private val newsState: MutableState<NewsState?> = mutableStateOf(null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    newsState = newsState,
                    onPostItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.POST_OPENED, AnalyticsEvents.NEWS_SHARED)
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        store.subscribe(this) { state ->
            state.skipRepeats { oldState, newState ->
                oldState.news == newState.news
            }.select { it.news }
        }
    }

    override fun onStop() {
        super.onStop()

        store.unsubscribe(this);
    }

    private fun openWebView(link: String, title: String, openEvent: String, shareEvent: String) {
        startActivity(
            WebViewActivity.newIntent(
                requireContext(),
                link,
                title,
                openEvent,
                shareEvent
            )
        )
    }

    override fun onNewState(state: NewsState) {
        newsState.value = state
    }
}

@Composable
private fun ContentView(
    newsState: State<NewsState?>,
    onPostItem: (String, String) -> Unit
) = LazyColumn(
    modifier = Modifier
        .fillMaxSize()
        .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 0.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    val state = newsState.value ?: return@LazyColumn

    items(state.news) {
        when(it) {
            is News.Post -> PostView(it, onPostItem)
            is News.PostWithComment -> PostWithCommentView(it, onPostItem)
            else -> Text("Another post")
        }
    }
}
