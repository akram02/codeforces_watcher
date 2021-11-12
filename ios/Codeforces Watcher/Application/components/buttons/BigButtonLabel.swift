import SwiftUI

struct BigButtonLabel: View {
    
    let label: String
    
    var foregroundColor: Color = Palette.white.swiftUIColor
    var backgroundColor: Color = Palette.black.swiftUIColor
    var borderWidth: CGFloat = 0
    
    init(
        label: String,
        isInverted: Bool
    ) {
        self.label = label
        
        if isInverted {
            self.foregroundColor = Palette.black.swiftUIColor
            self.backgroundColor = Palette.white.swiftUIColor
            self.borderWidth = 2
        }
    }
    
    var body: some View {
        Text(label)
            .font(.bodySemibold)
            .foregroundColor(foregroundColor)
            .frame(minWidth: 234)
            .frame(height: 20)
            .padding(.horizontal, 8)
            .padding(.vertical, 10)
            .background(backgroundColor)
            .clipShape(
                RoundedRectangle(cornerRadius: 30)
            )
            .overlay(
                RoundedRectangle(cornerRadius: 30)
                    .strokeBorder(foregroundColor, lineWidth: borderWidth)
            )
            .lineLimit(1)
    }
}

struct BigButtonLabel_Previews: PreviewProvider {
    static var previews: some View {
        BigButtonLabel(
            label: "Button",
            isInverted: false
        )
    }
}
