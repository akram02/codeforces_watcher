import SwiftUI

struct PinnedPostView: View {
    
    var post: NewsItem.PinnedItem
    var onNews: (_ title: String, _ link: String) -> Void = { _, _ in }
    
    @State private var isHidden = true
    
    private let animationDuration = 0.3
    
    var body: some View {
        if isHidden {
            Button(action: {
                toggle()
            }, label: {
                HiddenPinnedPostView(title: post.title)
            })
        } else {
            VisiblePinnedPostView(
                post: post,
                onNews: onNews,
                onCrossButton: {
                    toggle()
                }
            )
        }
    }
    
    private func toggle() {
        withAnimation(.easeIn(duration: animationDuration)) {
            isHidden.toggle()
        }
    }
}

fileprivate struct HiddenPinnedPostView: View {
    
    var title: String
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Image("logo")
                .frame(width: 32, height: 32)
            
            TitleView
        }
        .padding(.horizontal, 12)
        .frame(height: 60)
        .background(BackgroundView)
        .cornerRadius(20)
        .environment(\.colorScheme, .light)
    }
    
    @ViewBuilder
    private var TitleView: some View {
        VStack(alignment: .leading, spacing: 0) {
            LinearGradient(
                gradient: Gradient(colors: [
                    Palette.redGradient.swiftUIColor,
                    Palette.yellowGradient.swiftUIColor,
                    Palette.blueGradient.swiftUIColor
                ]),
                startPoint: .leading,
                endPoint: .trailing
            ).mask(
                CommonText(title)
                    .font(.midHeaderSemibold2)
                    .frame(maxWidth: .infinity, alignment: .leading)
            )
                .frame(height: 18)
            
            CommonText("Click here to see more")
                .font(.hintSemibold)
                .foregroundColor(Palette.white.swiftUIColor)
        }
        .lineLimit(1)
        .frame(maxWidth: .infinity)
    }
    
    @ViewBuilder
    private var BackgroundView: some View {
        LinearGradient(
            gradient: Gradient(stops: [
                .init(color: Palette.black.swiftUIColor.opacity(0.65), location: 0.26),
                .init(color: Palette.black.swiftUIColor.opacity(0), location: 0.92)
            ]),
            startPoint: .bottomLeading,
            endPoint: .topTrailing
        ).background(
            Image("pinnedPostBackground")
                .resizable()
                .scaledToFill()
                .frame(height: 60, alignment: .center)
                .clipped()
        )
    }
}

fileprivate struct VisiblePinnedPostView: View {
    
    var post: NewsItem.PinnedItem
    var onNews: (_ title: String, _ link: String) -> Void = { _, _ in }
    var onCrossButton: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading) {
            HeaderView
            
            Spacer()
                .frame(height: 72)
            
            TitleView
            
            Spacer()
                .frame(height: 12)
            
            SeeDetailsView
        }
        .padding(12)
        .background(BackgroundView)
        .cornerRadius(20)
        .environment(\.colorScheme, .light)
    }
    
    @ViewBuilder
    private var HeaderView: some View {
        HStack {
            Image("logo")
                .frame(width: 32, height: 32)
            
            Spacer()
            
            Button(action: {
                onCrossButton()
            }, label: {
                Image("crossIconNew")
            })
        }
    }
    
    @ViewBuilder
    private var TitleView: some View {
        LinearGradient(
            gradient: Gradient(colors: [
                Palette.redGradient.swiftUIColor,
                Palette.yellowGradient.swiftUIColor,
                Palette.blueGradient.swiftUIColor
            ]),
            startPoint: .leading,
            endPoint: .trailing
        )
            .mask(
                CommonText(post.title)
                    .font(SwiftUI.Font.system(size: 30, weight: .semibold, design: .monospaced))
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .lineLimit(2)
        )
            .frame(height: 72)
    }
    
    @ViewBuilder
    private var SeeDetailsView: some View {
        HStack {
            CommonText("Huge redesign, mentors & more!")
                .font(.bodySemibold)
                .foregroundColor(Palette.white.swiftUIColor)
                .lineLimit(2)
            
            Spacer()
            
            CommonSmallButton(
                label: "See details",
                action: {
                    onNews(post.title, post.link)
                },
                foregroundColor: Palette.black.swiftUIColor,
                backgroundColor: Palette.white.swiftUIColor
            )
        }
    }
    
    @ViewBuilder
    private var BackgroundView: some View {
        GeometryReader { geometry in
            RadialGradient(
                gradient: Gradient(stops: [
                    .init(color: Palette.black.swiftUIColor.opacity(0), location: 0.44),
                    .init(color: Palette.black.swiftUIColor.opacity(0.5), location: 0.88)
                ]),
                center: UnitPoint(x: 0.8, y: 0.2),
                startRadius: 1,
                endRadius: geometry.size.width * 0.6
            ).background(
                Image("pinnedPostBackground")
                    .resizable()
                    .scaledToFill()
            )
        }
    }
}
