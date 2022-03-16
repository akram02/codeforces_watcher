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
            newsState = null,
            feedbackItem = null
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
                    onPostFeedbackItem = { newsState.value.newsState?.let { onNewState(it) } },
                    onPostPinnedItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.PINNED_POST_OPENED, AnalyticsEvents.NEWS_SHARED)
                    },
                    onPostItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.POST_OPENED, AnalyticsEvents.NEWS_SHARED)
                    },
                    onPostVideoItem = { link, title ->
                        openWebView(link, title, AnalyticsEvents.VIDEO_OPENED, AnalyticsEvents.VIDEO_SHARED)
                    },
                    modifier = Modifier.fillMaxSize()
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
        store.dispatch(NewsRequests.FetchNews(isInitiatedByUser = true))
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

    override fun onNewState(state: NewsState) {
        val feedbackController = FeedbackController.get()
        var feedbackItem: NewsItem.FeedbackItem? = null

        if (feedbackController.shouldShowFeedbackCell()) {
            feedbackItem = NewsItem.FeedbackItem(feedbackController.feedUIModel)
        }

        newsState.value = newsState.value.copy(
            newsState = state,
            feedbackItem = feedbackItem
        )
    }
}

@Composable
private fun ContentView(
    newsState: State<UIModel>,
    onRefresh: () -> Unit,
    onPostFeedbackItem: () -> Unit,
    onPostPinnedItem: (String, String) -> Unit,
    onPostItem: (String, String) -> Unit,
    onPostVideoItem: (String, String) -> Unit,
    modifier: Modifier = Modifier
) = Scaffold(
    topBar = { NavigationBar() },
    backgroundColor = AlgoismeTheme.colors.primaryVariant
) {
    val state = newsState.value.newsState ?: return@Scaffold

    SwipeRefresh(
        state = rememberSwipeRefreshState(state.status == NewsState.Status.PENDING),
        onRefresh = { onRefresh() },
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
            .background(AlgoismeTheme.colors.primary)
    ) {
        if (state.news.isEmpty()) {
            NoItemsView(R.drawable.ic_no_items_news, R.string.news_on_the_way)
        } else {
            NewsList(
                news = state.news.filter {
                    if (it is News.PinnedPost) settings.readLastPinnedPostLink() != it.link else true
                },
                feedbackItem = newsState.value.feedbackItem,
                onPostFeedbackItem = onPostFeedbackItem,
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
    news: List<News>,
    feedbackItem: NewsItem.FeedbackItem?,
    onPostFeedbackItem: () -> Unit,
    onPostPinnedItem: (String, String) -> Unit,
    onPostItem: (String, String) -> Unit,
    onPostVideoItem: (String, String) -> Unit
) = LazyColumn(
    modifier = Modifier.padding(horizontal = 20.dp)
) {
    item {
        if (feedbackItem != null) PostFeedbackView(feedbackItem, onPostFeedbackItem)
    }

    items(news) {
        when (it) {
            is News.PinnedPost -> PostPinnedView(it, onPostPinnedItem)
            is News.Post -> PostView(it, onPostItem)
            is News.PostWithComment -> PostWithCommentView(it.post, it.comment, onPostItem)
            is News.Video -> PostVideoView(it, onPostVideoItem)
        }
    }
}

private data class UIModel(
    val newsState: NewsState?,
    val feedbackItem: NewsItem.FeedbackItem?
)