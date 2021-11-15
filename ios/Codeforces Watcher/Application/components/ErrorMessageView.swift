import SwiftUI

struct ErrorMessageView: View {
    
    var message: String
    
    var body: some View {
        Text(message)
            .font(.bodySemibold)
            .foregroundColor(Palette.black.swiftUIColor)
            .multilineTextAlignment(.center)
            .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
            .frame(minHeight: 40)
            .padding(.vertical, 16)
    }
}

struct ErrorMessageView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorMessageView(message: "Error!")
    }
}
