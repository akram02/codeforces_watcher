import SwiftUI
import common

class NewsViewControllerNew: UIHostingController<NewsView>, ReKampStoreSubscriber {
    
    private lazy var fabButton = FabButtonViewController(name: "newsShareIcon")
    
    init() {
        super.init(rootView: NewsView())
        
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
        setFabButton()
        fabButton.show()
        
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
        
        fabButton.hide()
        
        store.unsubscribe(subscriber: self)
    }
    
    private func setFabButton() {
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setButtonAction(action: { self.onFabButton() })
    }
    
    private func onFabButton() {
        let activityController = UIActivityViewController(activityItems: ["share_cw_message".localized],
                                                          applicationActivities: nil).apply {
            $0.popoverPresentationController?.run {
                $0.sourceView = self.view
                $0.sourceRect = CGRect(x: self.view.bounds.midX, y: self.view.bounds.height, width: 0, height: 0)
            }
        }
        
        present(activityController, animated: true)
        
        analyticsControler.logEvent(eventName: AnalyticsEvents().SHARE_APP, params: [:])
    }
    
    private func setInteractions() {
        rootView.onPostWithCommentItem = { title, link in
            let title = buildShareText(title, link)
            let onOpenEvent = AnalyticsEvents().POST_OPENED
            let onShareEvent = AnalyticsEvents().NEWS_SHARED
            
            self.openWebViewController(link, title, onOpenEvent, onShareEvent)
        }
        
        rootView.onPostItem = { title, link in
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
        self.presentModal(webViewController)
    }
    
    func onNewState(state: Any) {
        let state = state as! NewsState
        var items: [NewsItem] = []
        
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
