import SwiftUI

struct MainView: View {
    
    var selectedIndex: Int = 0
    
    var container: ViewControllerContainer
    
    var fabButtonIconName: String = ""
    var onFabButton: () -> Void = {}
    
    var onTabItem: (_ index: Int) -> Void = { _ in }
    
    @State private var geometryHeight: CGFloat = 0
    
    var body: some View {
        VStack(spacing: 0) {
            container
            
            GeometryReader { geometry in
                ZStack {
                    HStack(alignment: .bottom) {
                        ButtonItem(index: 0, title: "Contests", iconName: "contestsIcon")
                        Spacer()
                        ButtonItem(index: 1, title: "Users", iconName: "usersIcon")
                        Spacer()
                            .frame(width: geometry.size.width * 0.15)
                        ButtonItem(index: 2, title: "News", iconName: "newsIcon")
                        Spacer()
                        ButtonItem(index: 3, title: "Problems", iconName: "problemsIcon")
                    }
                    .frame(height: 60)
                    .background(Palette.accentGrayish.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
                    .shadow(color: Palette.white.swiftUIColor, radius: 40, x: 0, y: -20)
                    .background(GeometryReader { g -> Color in
                        DispatchQueue.main.async {
                            self.geometryHeight = g.size.height
                        }
                        return Color.clear
                    })
                    
                    FabButtonView()
                }
            }
            .frame(height: geometryHeight)
        }
        .edgesIgnoringSafeArea(.top)
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
    
    @ViewBuilder
    private func FabButtonView() -> some View {
        Button(action: {
            onFabButton()
        }, label: {
            Image(fabButtonIconName)
                .renderingMode(.original)
        }).offset(y: -16)
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
