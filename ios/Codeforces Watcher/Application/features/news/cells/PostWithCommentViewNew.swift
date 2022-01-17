import SwiftUI

struct PostWithCommentViewNew: View {
    
    var post: NewsItem.PostWithCommentItem
    var onNews: (
        _ title: String,
        _ link: String
    ) -> () = { _, _ in }
    
    var body: some View {
        VStack(spacing: 10) {
            PostTextView(
                content: post.postContent,
                authorAvatar: post.postAuthorAvatar,
                authorHandle: post.postAuthorHandle,
                authorRankColor: post.postAuthorRankColor,
                title: post.blogTitle,
                agoText: post.postAgoText
            )
                .onTapGesture {
                    onNews(post.blogTitle, post.postLink)
                }
            
            CommentView(
                commentatorAvatar: post.commentatorAvatar,
                commentatorRankColor: post.commentatorRankColor,
                commentatorHandle: post.commentatorHandle,
                commentAgoText: post.commentAgoText,
                commentContent: post.commentContent
            )
                .onTapGesture {
                    onNews(post.blogTitle, post.commentLink)
                }
            
            AllCommentsView
                .onTapGesture {
                    onNews(post.blogTitle, post.postLink)
                }
        }
        .background(Palette.lightGray.swiftUIColor)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var AllCommentsView: some View {
        HStack {
            CommonText("See all comments")
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
            
            Spacer()
            
            Image("ic_arrow")
                .resizable()
                .frame(width: 12, height: 12)
        }
        .frame(height: 32)
        .padding(.horizontal, 12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}

fileprivate struct CommentView: View {
    
    var commentatorAvatar: String
    var commentatorRankColor: CGColor
    var commentatorHandle: NSAttributedString
    var commentAgoText: String
    var commentContent: String
    
    var body: some View {
        HStack(alignment: .top, spacing: 6) {
            CircleImageViewNew(
                userAvatar: commentatorAvatar,
                borderColor: Color(commentatorRankColor),
                size: (width: 36, height: 36)
            )
            
            VStack(spacing: 4) {
                CommentDetail
                
                CommonText(commentContent)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .lineLimit(2)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .font(.hintRegular)
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .background(Palette.accentGrayish.swiftUIColor)
            .cornerRadius(15, corners: [.topRight, .bottomLeft, .bottomRight])
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 12)
        .background(Color.clear)
    }
    
    private var CommentDetail: some View {
        HStack(spacing: 0) {
            AttributedTextView(
                attributedString: commentatorHandle as! NSMutableAttributedString,
                font: Font.monospacedHintRegular,
                alignment: .left
            )
                .fixedSize()
            
            CommonText(commentAgoText)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}
