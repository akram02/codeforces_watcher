import SwiftUI

struct NewsView: View {
    
    var news: [NewsItem] = []
    
    var onFeedbackItemCallback: () -> Void = {}
    var onPinnedPostItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onPostItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onPostWithCommentItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onVideoItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            NewsNavigationBar(title: "News".localized)
            
            RefreshableScrollView(content: {
                if !news.isEmpty {
                    NewsList
                }
            }, refreshControl: refreshControl)
                .background(Palette.white.swiftUIColor)
                .cornerRadius(30, corners: [.topLeft, .topRight])
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    private var NewsList: some View {
        ScrollView {
            LazyVStack(spacing: 12) {
                ForEach(news.indices, id: \.self) { index in
                    switch (news[index]) {
                    case .feedbackItem(let item):
                        FeedbackView(post: item, callback: onFeedbackItemCallback)
                    case .pinnedItem(let item):
                        PinnedPostView(post: item, onNews: onPinnedPostItem)
                    case .postItem(let item):
                        PostView(post: item, onNews: onPostItem)
                    case .postWithCommentItem(let item):
                        PostWithCommentView(post: item, onNews: onPostWithCommentItem)
                    case .videoItem(let item):
                        PostVideoView(post: item, onNews: onVideoItem)
                    }
                }
            }
            .padding([.horizontal, .top], 20)
        }
    }
}

struct NewsView_Previews: PreviewProvider {
    static var previews: some View {
        NewsView()
    }
}
