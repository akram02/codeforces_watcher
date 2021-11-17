import SwiftUI

struct CommonBigButton: View {
    
    let label: String
    var action: () -> Void = {}
    
    var foregroundColor: Color = Palette.white.swiftUIColor
    var backgroundColor: Color = Palette.black.swiftUIColor
    var borderWidth: CGFloat = 0
    
    init(
        label: String,
        action: @escaping () -> Void,
        isInverted: Bool
    ) {
        self.label = label
        self.action = action
        
        if isInverted {
            self.foregroundColor = Palette.black.swiftUIColor
            self.backgroundColor = Palette.white.swiftUIColor
            self.borderWidth = 2
        }
    }
    
    var body: some View {
        Button(action: {
            self.action()
        }, label: {
            CommonText(label)
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
        })
    }
}

struct CommonBigButton_Previews: PreviewProvider {
    static var previews: some View {
        CommonBigButton(
            label: "Button",
            action: {},
            isInverted: false
        )
    }
}
