import SwiftUI

struct ErrorMessage: View {
    
    var message: String
    
    var body: some View {
        Text(message)
            .font(.primarySemibold)
            .foregroundColor(Palette.black.swiftUIColor)
            .multilineTextAlignment(.center)
            .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
            .frame(minHeight: 40)
            .padding(.vertical, 16)
    }
}

struct ErrorMessage_Previews: PreviewProvider {
    static var previews: some View {
        ErrorMessage(message: "Error!")
    }
}
