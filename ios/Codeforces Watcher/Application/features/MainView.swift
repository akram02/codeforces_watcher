import SwiftUI

struct MainView: View {
    
    var selectedIndex: Int = 0
    var container: ViewControllerContainer
    
    var onTabItem: (_ index: Int) -> Void = { _ in }
    
    var body: some View {
        VStack(spacing: 0) {
            container
            
            HStack {
                ButtonItem(index: 0, title: "Contests", iconName: "contestsIcon")
                Spacer()
                ButtonItem(index: 1, title: "Users", iconName: "usersIcon")
                Spacer()
                ButtonItem(index: 2, title: "News", iconName: "newsIcon")
                Spacer()
                ButtonItem(index: 3, title: "Problems", iconName: "problemsIcon")
            }
            .frame(height: 60)
            .background(Palette.accentGrayish.swiftUIColor)
            .cornerRadius(30, corners: [.topLeft, .topRight])
            .shadow(color: Palette.white.swiftUIColor, radius: 40, x: 0, y: -20)
        }
    }
    
    @ViewBuilder
    private func ButtonItem(
        index: Int,
        title: String,
        iconName: String
    ) -> some View {
        VStack(spacing: 2) {
            Image(iconName)
                .modifier(ColorScheme(isSelected: selectedIndex == index))
                .frame(width: 24, height: 24)
            
            Text(title)
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .onTapGesture {
            onTabItem(index)
        }
    }
}

fileprivate struct ColorScheme: ViewModifier {
    
    var isSelected: Bool
    
    func body(content: Content) -> some View {
        if isSelected {
            LinearGradient(
                gradient: Gradient(stops: [
                    .init(color: Palette.redGradient.swiftUIColor, location: 0.06),
                    .init(color: Palette.yellowGradient.swiftUIColor, location: 0.55),
                    .init(color: Palette.blueGradient.swiftUIColor, location: 0.92)
                ]),
                startPoint: .topTrailing,
                endPoint: .bottomLeading
            ).mask(content)
        } else {
            content
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
    }
}
