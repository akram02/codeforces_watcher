import SwiftUI

struct NewsView: View {
    
    var news: [NewsItem] = []
    
    var onPostWithCommentItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onPostItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onVideoItem: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onFeedbackItemCallback: () -> Void = {}
    
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
                    case .postWithCommentItem(let item):
                        PostWithCommentViewNew(post: item, onNews: onPostWithCommentItem)
                    case .postItem(let item):
                        PostView(post: item, onNews: onPostItem)
                    case .pinnedItem(let item):
                        PinnedPostViewNew()
                    case .feedbackItem(let item):
                        FeedbackViewNew(post: item, callback: onFeedbackItemCallback)
                    case .videoItem(let item):
                        VideoView(post: item, onNews: onVideoItem)
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
