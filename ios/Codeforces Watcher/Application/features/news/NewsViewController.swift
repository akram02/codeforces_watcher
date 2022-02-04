import SwiftUI
import common

class NewsViewController: UIHostingController<NewsView>, ReKampStoreSubscriber {
    
    init() {
        super.init(rootView: NewsView())
        
        setRefreshControl()
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                KotlinBoolean(bool: oldState.news == newState.news)
            }.select { state in
                state.news
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        store.unsubscribe(subscriber: self)
    }
    
    func onFabButton() {
        let activityController = UIActivityViewController(
            activityItems: ["share_cw_message".localized],
            applicationActivities: nil
        ).apply {
            $0.popoverPresentationController?.run {
                $0.sourceView = self.view
                $0.sourceRect = CGRect(x: self.view.bounds.midX, y: self.view.bounds.height, width: 0, height: 0)
            }
        }
        
        present(activityController, animated: true)
        
        analyticsControler.logEvent(eventName: AnalyticsEvents().SHARE_APP, params: [:])
    }
    
    private func setRefreshControl() {
        rootView.refreshControl.run {
            $0.addTarget(self, action: #selector(refreshNews(_:)), for: .valueChanged)
            $0.tintColor = Palette.black
        }
    }
    
    private func setInteractions() {
        rootView.onPinnedPostItem = { title, link in
            let onOpenEvent = AnalyticsEvents().PINNED_POST_OPENED
            let onShareEvent = AnalyticsEvents().NEWS_SHARED
            
            self.openWebViewController(link, title, onOpenEvent, onShareEvent)
        }
        
        rootView.onPostItem = { title, link in
            let onOpenEvent = AnalyticsEvents().POST_OPENED
            let onShareEvent = AnalyticsEvents().NEWS_SHARED
            
            self.openWebViewController(link, title, onOpenEvent, onShareEvent)
        }
        
        rootView.onPostWithCommentItem = { title, link in
            let title = buildShareText(title, link)
            let onOpenEvent = AnalyticsEvents().POST_OPENED
            let onShareEvent = AnalyticsEvents().NEWS_SHARED
            
            self.openWebViewController(link, title, onOpenEvent, onShareEvent)
        }
        
        rootView.onVideoItem = { title, link in
            let onOpenEvent = AnalyticsEvents().VIDEO_OPENED
            let onShareEvent = AnalyticsEvents().VIDEO_SHARED
            
            self.openWebViewController(link, title, onOpenEvent, onShareEvent)
        }
    }
    
    private func openWebViewController(
        _ link: String,
        _ title: String,
        _ onOpenEvent: String,
        _ onShareEvent: String
    ) {
        let webViewController = WebViewController(
            link,
            title,
            onOpenEvent,
            onShareEvent
        )
        presentModal(webViewController)
    }
    
    func onNewState(state: Any) {
        let state = state as! NewsState
        var items: [NewsItem] = []
        
        if state.status == .idle {
            rootView.refreshControl.endRefreshing()
            
            if feedbackController.shouldShowFeedbackCell() {
                items.append(NewsItem.feedbackItem(NewsItem.FeedbackItem(feedbackController.feedUIModel)))
                rootView.onFeedbackItemCallback = {
                    self.onNewState(state: state)
                }
            }
        }
        
        let news = state.news.filter { news in
            if let news = news as? News.PinnedPost {
                return SettingsKt.settings.readLastPinnedPostLink() != news.link
            } else {
                return true
            }
        }
        
        items += news.mapToItems()
        rootView.news = items
    }
    
    @objc private func refreshNews(_ sender: Any) {
        analyticsControler.logEvent(eventName: AnalyticsEvents().NEWS_REFRESH, params: [:])
        store.dispatch(action: NewsRequests.FetchNews(isInitiatedByUser: true))
    }
}


fileprivate extension Array where Element == News {
    func mapToItems() -> [NewsItem] {
        compactMap { news in
            switch(news) {
            case let postWithComment as News.PostWithComment:
                return NewsItem.postWithCommentItem(NewsItem.PostWithCommentItem(postWithComment.comment, postWithComment.post))
            case let post as News.Post:
                return NewsItem.postItem(NewsItem.PostItem(post))
            case let pinnedPost as News.PinnedPost:
                return NewsItem.pinnedItem(NewsItem.PinnedItem(pinnedPost))
            case let video as News.Video:
                return NewsItem.videoItem(NewsItem.VideoItem(video))
            default:
                return nil
            }
        }
    }
}
