import SwiftUI

struct NewsNavigationBar: View {
    
    var title: String
    
    var body: some View {
        CommonText(title)
            .font(.headerMedium)
            .foregroundColor(Palette.black.swiftUIColor)
            .frame(height: 56)
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(.horizontal, 20)
    }
}

struct NewsNavigationBar_Previews: PreviewProvider {
    static var previews: some View {
        NewsNavigationBar(title: "News".localized)
    }
}
