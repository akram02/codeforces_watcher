import SwiftUI

struct PinnedPostViewNew: View {
    
    @State var isHidden = true
    
    var body: some View {
        if isHidden {
            Button(action: {
                isHidden.toggle()
            }, label: {
                HiddenPinnedPostView()
            })
        } else {
            VisiblePinnedPostView(onCrossButton: {
                isHidden.toggle()
            })
        }
    }
}

fileprivate struct HiddenPinnedPostView: View {
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Image("logo")
                .frame(width: 32, height: 32)
            
            TitleView
        }
        .padding(.horizontal, 12)
        .frame(height: 60)
        .background(Background)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var TitleView: some View {
        VStack(alignment: .leading, spacing: 0) {
            LinearGradient(
                gradient: Gradient(colors: [
                    GradientPalette.red.swiftUIColor,
                    GradientPalette.yellow.swiftUIColor,
                    GradientPalette.blue.swiftUIColor
                ]),
                startPoint: .leading,
                endPoint: .trailing
            ).mask(
                CommonText("Update 3.0: What’s New?")
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
    private var Background: some View {
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
    
    var onCrossButton: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading) {
            Header
            
            Spacer()
                .frame(height: 72)
            
            TitleView
            
            Spacer()
                .frame(height: 12)
            
            SeeDetailsView
        }
        .padding(12)
        .background(Background)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var Header: some View {
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
    
    private var TitleView: some View {
        LinearGradient(
            gradient: Gradient(colors: [
                GradientPalette.red.swiftUIColor,
                GradientPalette.yellow.swiftUIColor,
                GradientPalette.blue.swiftUIColor
            ]),
            startPoint: .leading,
            endPoint: .trailing
        )
            .mask(
            CommonText("Update 3.0: What’s New?")
                .font(SwiftUI.Font.system(size: 30, weight: .semibold, design: .monospaced))
        )
            .frame(width: 200, height: 72)
    }
    
    private var SeeDetailsView: some View {
        HStack {
            CommonText("Huge redesign, mentors & more!")
                .font(.bodySemibold)
                .foregroundColor(Palette.white.swiftUIColor)
                .lineLimit(2)
            
            Spacer()
            
            CommonSmallButton(
                label: "See details",
                action: {},
                foregroundColor: Palette.black.swiftUIColor,
                backgroundColor: Palette.white.swiftUIColor
            )
        }
    }
    
    @ViewBuilder
    private var Background: some View {
        GeometryReader { geometry in
            RadialGradient(
                gradient: Gradient(stops: [
                    .init(color: Palette.black.swiftUIColor.opacity(0), location: 0.44),
                    .init(color: Palette.black.swiftUIColor.opacity(0.6), location: 0.88)
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

struct PinnedPostViewNew_Previews: PreviewProvider {
    static var previews: some View {
        PinnedPostViewNew()
    }
}
