import SwiftUI

struct ButtonTextDefault: View {
    
    var text: String
    
    var body: some View {
        Text(text)
            .font(.primarySemibold)
            .foregroundColor(.white)
            .frame(minWidth: 234)
            .frame(height: 20)
            .padding(.horizontal, 8)
            .padding(.vertical, 10)
            .background(Palette.black.swiftUIColor)
            .cornerRadius(30)
    }
}

struct ButtonTextDefault_Previews: PreviewProvider {
    static var previews: some View {
        ButtonTextDefault(text: "Text")
    }
}
