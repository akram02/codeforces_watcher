import SwiftUI

struct MainView: View {
    
    @State private var selectedIndex = 0
    
    var body: some View {
        HStack {
            ButtonItem(index: 0, title: "Contests", iconName: "contestsIcon")
            Spacer()
            ButtonItem(index: 1, title: "Users", iconName: "usersIcon")
            Spacer()
            ButtonItem(index: 2, title: "News", iconName: "newsIcon")
            Spacer()
            ButtonItem(index: 3, title: "Problems", iconName: "problemsIcon")
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
            selectedIndex = index
        }
    }
}

fileprivate struct ColorScheme: ViewModifier {
    
    var isSelected: Bool
    
    func body(content: Content) -> some View {
        if isSelected {
            LinearGradient(
                gradient: Gradient(stops: [
                    .init(color: PalleteGradient.redGradient.swiftUIColor, location: 0.06),
                    .init(color: PalleteGradient.yellowGradient.swiftUIColor, location: 0.55),
                    .init(color: PalleteGradient.blueGradient.swiftUIColor, location: 0.92)
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

fileprivate class PalleteGradient {
    
    public static let redGradient = UIColor(rgb: 0xC60706)
    public static let yellowGradient = UIColor(rgb: 0xFFCA00)
    public static let blueGradient = UIColor(rgb: 0x1289CE)
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView()
    }
}
