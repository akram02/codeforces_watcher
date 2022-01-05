import SwiftUI

struct NoItemsView: View {
    
    var imageName: String
    var text: String
    
    var body: some View {
        GeometryReader { geometry in
            VStack(alignment: .center, spacing: 34) {
                Image(imageName)
                
                CommonText(text.localized)
                    .font(.headerMedium)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                    .multilineTextAlignment(.center)
                    .fixedSize(horizontal: false, vertical: true)
            }
            .frame(width: geometry.size.width, height: geometry.size.height, alignment: .center)
        }
        .padding(.horizontal, 20)
    }
}

struct NoItemsView_Previews: PreviewProvider {
    static var previews: some View {
        NoItemsView(imageName: "noItemsProblems", text: "problems_explanation")
    }
}
