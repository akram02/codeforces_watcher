import SwiftUI

struct PostView: View {
    
    var post: NewsItem.PostItem
    var onNews: (
        _ title: String,
        _ link: String
    ) -> () = { _, _ in }
    
    var body: some View {
        VStack(spacing: 0) {
            PostTextView(
                content: post.content,
                authorAvatar: post.authorAvatar,
                authorHandle: post.authorHandle,
                authorRankColor: post.rankColor,
                title: post.blogTitle,
                agoText: post.agoText
            )
            
            CommentView
        }
        .background(Palette.lightGray.swiftUIColor)
        .cornerRadius(20)
        .onTapGesture {
            onNews(post.blogTitle, post.link)
        }
    }
    
    @ViewBuilder
    private var CommentView: some View {
        HStack {
            CommonText("Be the first to comment")
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
            
            Spacer()
            
            Image("ic_arrow")
                .resizable()
                .frame(width: 12, height: 12)
        }
        .frame(height: 32)
        .padding(.horizontal, 12)
        .background(Color.clear)
        .cornerRadius(20)
    }
}
