import SwiftUI

extension View {
    
    func screenBackground() -> some View {
        self
            .background(Palette.white.swiftUIColor)
            .cornerRadius(30, corners: [.topLeft, .topRight])
    }
    
    func cornerRadius(_ radius: CGFloat, corners: UIRectCorner) -> some View {
        clipShape(RoundedCorner(radius: radius, corners: corners) )
    }
}
