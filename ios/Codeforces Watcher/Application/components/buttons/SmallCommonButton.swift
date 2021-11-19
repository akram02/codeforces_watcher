import SwiftUI

struct SmallCommonButton: View {
    
    let label: String
    var action: () -> Void = {}
    let foregroundColor: Color
    let backgroundColor: Color
    var borderColor: Color = Palette.black.swiftUIColor
    var borderWidth: CGFloat = 0
    
    var body: some View {
        Button(action: {
            self.action()
        }, label: {
            Text(label)
                .font(.hintSemibold)
                .foregroundColor(foregroundColor)
                .frame(minWidth: 60)
                .frame(height: 16)
                .padding(8)
                .background(backgroundColor)
                .clipShape(
                     RoundedRectangle(cornerRadius: 30)
                 )
                 .overlay(
                     RoundedRectangle(cornerRadius: 30)
                        .strokeBorder(borderColor, lineWidth: borderWidth)
                 )
                 .lineLimit(1)
        })
    }
}

struct SmallCommonButton_Previews: PreviewProvider {
    static var previews: some View {
        SmallCommonButton(
            label: "Text",
            foregroundColor: Palette.black.swiftUIColor,
            backgroundColor: Palette.white.swiftUIColor
        )
    }
}
