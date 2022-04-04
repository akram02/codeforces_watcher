import SwiftUI

struct MainView: View {
    
    var selectedIndex: Int = 0
    
    var container: ViewControllerContainer
    
    var fabButtonIconName: String = ""
    var onFabButton: () -> Void = {}
    
    var onTabItem: (_ index: Int) -> Void = { _ in }
    
    @State private var geometryHeight: CGFloat = 0
    
    private let bottomSafeAreaHeight = UIApplication.shared.windows.first?.safeAreaInsets.bottom
    
    var body: some View {
        VStack(spacing: 0) {
            container
            
            GeometryReader { geometry in
                ZStack {
                    HStack(alignment: .bottom) {
                        ButtonItemView(index: 0, title: "Contests", iconName: "contestsIcon")
                        Spacer()
                        ButtonItemView(index: 1, title: "Users", iconName: "usersIcon")
                        Spacer()
                            .frame(width: geometry.size.width * 0.15)
                        ButtonItemView(index: 2, title: "News", iconName: "newsIcon")
                        Spacer()
                        ButtonItemView(index: 3, title: "Problems", iconName: "problemsIcon")
                    }
                    .frame(height: 60)
                    .background(Palette.accentGrayish.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
                    .shadow(color: Palette.white.swiftUIColor, radius: 20)
                    .background(GeometryReader { g -> Color in
                        DispatchQueue.main.async {
                            self.geometryHeight = g.size.height
                        }
                        return Color.clear
                    })
                    
                    FabButtonView()
                        .frame(width: 56, height: 56)
                }
            }
            .frame(height: geometryHeight)
            
            Palette.accentGrayish.swiftUIColor
                .frame(height: UIApplication.shared.windows.first?.safeAreaInsets.bottom)
        }
        .edgesIgnoringSafeArea(.all)
    }
    
    @ViewBuilder
    private func ButtonItemView(
        index: Int,
        title: String,
        iconName: String
    ) -> some View {
        VStack(spacing: 2) {
            Image(iconName)
                .renderingMode(.template)
                .modifier(ColorScheme(title: title, isSelected: selectedIndex == index))
                .frame(width: 24, height: 24)
            
            Text(title)
                .font(.hintRegular)
                .foregroundColor(Palette.mediumGray.swiftUIColor)
                .lineLimit(1)
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
    
    var title: String
    var isSelected: Bool
    
    private let gradientLocations: [String : [CGFloat]] = [
        "Contests": [0.2, 0.55, 0.80],
        "Users": [0.2, 0.55, 0.85],
        "News": [0.07, 0.55, 0.8],
        "Problems": [0.2, 0.53, 0.70]
    ]
    
    func body(content: Content) -> some View {
        if isSelected {
            LinearGradient(
                gradient: Gradient(stops: [
                    .init(color: Palette.redGradient.swiftUIColor, location: gradientLocations[title]![0]),
                    .init(color: Palette.yellowGradient.swiftUIColor, location: gradientLocations[title]![1]),
                    .init(color: Palette.blueGradient.swiftUIColor, location: gradientLocations[title]![2])
                ]),
                startPoint: .topTrailing,
                endPoint: .bottomLeading
            ).mask(content)
        } else {
            content
                .foregroundColor(Palette.mediumGray.swiftUIColor)
        }
    }
}
