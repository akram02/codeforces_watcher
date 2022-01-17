import SwiftUI

struct NewsView: View {
    
    var news: [NewsItem] = []
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
                FeedbackViewNew()
                
                VideoView()
                
                PostWithCommentViewNew()
                
                PostView()
                
                ForEach(news.indices, id: \.self) { index in
                    switch (news[index]) {
                    case .postWithCommentItem(let item):
                        CommonText(item.blogTitle)
                    case .postItem(let item):
                        CommonText(item.blogTitle)
                    case .pinnedItem(let item):
                        CommonText(item.title)
                    case .feedbackItem(let item):
                        CommonText(item.textTitle)
                    case .videoItem(let item):
                        CommonText(item.title)
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
