import SwiftUI

struct NewsView: View {
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            NewsNavigationBar(title: "News".localized)
            
            RefreshableScrollView(content: {
                NewsList
            }, refreshControl: refreshControl)
                .background(Palette.white.swiftUIColor)
                .cornerRadius(30, corners: [.topLeft, .topRight])
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    private var NewsList: some View {
        ScrollView {
            LazyVStack {
                VideoView()
            }
            .padding(.horizontal, 20)
        }
    }
}

struct NewsView_Previews: PreviewProvider {
    static var previews: some View {
        NewsView()
    }
}
