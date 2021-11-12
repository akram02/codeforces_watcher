import SwiftUI

struct ButtonTextInverse: View {
    
    var text: String
    
    var body: some View {
        Text(text)
            .font(.bodySemibold)
            .foregroundColor(Palette.black.swiftUIColor)
            .frame(minWidth: 232)
            .frame(height: 18)
            .padding(.horizontal, 8)
            .padding(.vertical, 10)
            .background(Palette.white.swiftUIColor)
            .overlay(
                RoundedRectangle(cornerRadius: 30)
                    .stroke(Palette.black.swiftUIColor, lineWidth: 2)
            )
    }
}

struct ButtonTextInverse_Previews: PreviewProvider {
    static var previews: some View {
        ButtonTextInverse(text: "Text")
    }
}
