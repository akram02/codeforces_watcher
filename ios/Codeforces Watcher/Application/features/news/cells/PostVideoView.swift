import SwiftUI
import SDWebImageSwiftUI

struct PostVideoView: View {
    
    var post: NewsItem.VideoItem
    var onNews: (_ title: String, _ link: String) -> () = { _, _ in }
    
    @State private var geometryHeight: CGFloat = 0
    
    var body: some View {
        VStack(spacing: 12) {
            PostTitleView(
                authorAvatar: post.authorAvatar,
                authorHandle: post.authorHandle,
                authorRankColor: post.rankColor,
                title: post.title,
                agoText: post.agoText
            )
            
            ThumbnailImageView
        }
        .frame(maxWidth: .infinity)
        .padding(12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .onTapGesture {
            onNews(post.title, post.link)
        }
    }
    
    @ViewBuilder
    private var ThumbnailImageView: some View {
        GeometryReader { geometry in
            WebImage(url: URL(string: post.thumbnailLink))
                .resizable()
                .placeholder(Image("video_placeholder"))
                .scaledToFill()
                .frame(maxHeight: geometry.size.width / 16.0 * 9, alignment: .center)
                .clipped()
                .cornerRadius(10)
                .background(GeometryReader { g -> Color in
                    DispatchQueue.main.async {
                        self.geometryHeight = g.size.height
                    }
                    return Color.clear
                })
        }
        .frame(height: geometryHeight)
    }
}
