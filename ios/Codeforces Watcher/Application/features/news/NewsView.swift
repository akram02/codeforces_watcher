import SwiftUI

struct NewsView: View {
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            NewsNavigationBar(title: "News".localized)
            
            RefreshableScrollView(content: {
                CommonText("News")
            }, refreshControl: refreshControl)
                .background(Palette.white.swiftUIColor)
                .cornerRadius(30, corners: [.topLeft, .topRight])
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
}

struct NewsView_Previews: PreviewProvider {
    static var previews: some View {
        NewsView()
    }
}
