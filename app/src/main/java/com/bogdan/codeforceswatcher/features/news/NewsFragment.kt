package com.bogdan.codeforceswatcher.features.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.WebViewActivity
import com.bogdan.codeforceswatcher.components.compose.NoItemsView
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.cells.*
import com.bogdan.codeforceswatcher.features.news.models.NewsItem
import com.bogdan.codeforceswatcher.util.FeedbackController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.xorum.codeforceswatcher.features.news.models.News
import io.xorum.codeforceswatcher.features.news.redux.NewsRequests
import io.xorum.codeforceswatcher.features.news.redux.NewsState
import io.xorum.codeforceswatcher.redux.analyticsController
import io.xorum.codeforceswatcher.redux.store
import io.xorum.codeforceswatcher.util.AnalyticsEvents
import io.xorum.codeforceswatcher.util.settings
import tw.geothings.rekotlin.StoreSubscriber

class NewsFragment : Fragment(), StoreSubscriber<NewsState> {

    private val newsState: MutableState<UIModel> = mutableStateOf(
        UIModel(
            news = emptyList(),
            feedbackCallback = {},
            isRefreshing = false
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView(
                    newsState = newsState,
                    onRefresh = { onRefresh() },
                    onPostPinnedItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.PINNED_POST_OPENED, AnalyticsEvents.NEWS_SHARED)
                    },
                    onPostItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.POST_OPENED, AnalyticsEvents.NEWS_SHARED)
                    },
                    onPostVideoItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.VIDEO_OPENED, AnalyticsEvents.VIDEO_SHARED)
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

        store.unsubscribe(this)
    }

    private fun onRefresh() {
        store.dispatch(NewsRequests.FetchNews(true))
        analyticsController.logEvent(AnalyticsEvents.NEWS_REFRESH)
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

    private fun buildNewsItems(news: List<News>) = news.map {
        when (it) {
            is News.Post -> NewsItem.PostItem(it)
            is News.PinnedPost -> NewsItem.PinnedItem(it)
            is News.PostWithComment -> NewsItem.PostWithCommentItem(it.post, it.comment)
            is News.Video -> NewsItem.VideoItem(it)
        }
    }

    override fun onNewState(state: NewsState) {
        val items = mutableListOf<NewsItem>()

        val feedbackController = FeedbackController.get()
        var feedbackCallback: () -> Unit = {}

        if (feedbackController.shouldShowFeedbackCell()) {
            items.add(NewsItem.FeedbackItem(feedbackController.feedUIModel))
            feedbackCallback = { onNewState(state) }
        }

        val news = state.news.filter {
            return@filter if (it is News.PinnedPost) settings.readLastPinnedPostLink() != it.link else true
        }
        val newsItems = buildNewsItems(news)
        items.addAll(newsItems)

        newsState.value = newsState.value.copy(
            news = items,
            feedbackCallback = feedbackCallback,
            isRefreshing = state.status == NewsState.Status.PENDING
        )
    }
}

@Composable
private fun ContentView(
    newsState: State<UIModel>,
    onRefresh: () -> Unit,
    onPostPinnedItem: (String, String) -> Unit,
    onPostItem: (String, String) -> Unit,
    onPostVideoItem: (String, String) -> Unit
) = Scaffold(
    modifier = Modifier.fillMaxWidth(),
    topBar = { NavigationBar() },
    backgroundColor = AlgoismeTheme.colors.primaryVariant
) {
    val state = newsState.value

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.isRefreshing),
        onRefresh = { onRefresh() },
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
    ) {
        if (state.news.isEmpty()) {
            NoItemsView(R.drawable.ic_no_items_news, R.string.news_on_the_way)
        } else {
            NewsList(
                news = state.news,
                onPostFeedbackItem = state.feedbackCallback,
                onPostPinnedItem = onPostPinnedItem,
                onPostItem = onPostItem,
                onPostVideoItem = onPostVideoItem
            )
        }
    }
}

@Composable
private fun NavigationBar() = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 25.dp),
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = stringResource(R.string.news),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.secondary
    )
}

@Composable
private fun NewsList(
    news: List<NewsItem>,
    onPostFeedbackItem: () -> Unit,
    onPostPinnedItem: (String, String) -> Unit,
    onPostItem: (String, String) -> Unit,
    onPostVideoItem: (String, String) -> Unit
) = LazyColumn(
    modifier = Modifier.padding(horizontal = 20.dp)
) {
    items(news) {
        when (it) {
            is NewsItem.FeedbackItem -> PostFeedbackView(it, onPostFeedbackItem)
            is NewsItem.PinnedItem -> PostPinnedView(it, onPostPinnedItem)
            is NewsItem.PostItem -> PostView(it, onPostItem)
            is NewsItem.PostWithCommentItem -> PostWithCommentView(it, onPostItem)
            is NewsItem.VideoItem -> PostVideoView(it, onPostVideoItem)
        }
    }
}

private data class UIModel(
    val news: List<NewsItem>,
    val feedbackCallback: () -> Unit,
    val isRefreshing: Boolean
)