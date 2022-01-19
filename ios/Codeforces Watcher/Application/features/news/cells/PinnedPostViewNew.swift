import SwiftUI

struct PinnedPostViewNew: View {
    
    var body: some View {
        HStack(alignment: .top, spacing: 12) {
            Image("logo")
                .frame(width: 32, height: 32)
            
            TextView
        }
        .padding(.horizontal, 12)
        .frame(height: 60)
        .background(SmallBackground)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var TextView: some View {
        VStack(spacing: 0) {
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
                .frame(maxWidth: .infinity, alignment: .leading)
        }
        .lineLimit(1)
    }
    
    @ViewBuilder
    private var SmallBackground: some View {
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

struct PinnedPostViewNew_Previews: PreviewProvider {
    static var previews: some View {
        PinnedPostViewNew()
    }
}
