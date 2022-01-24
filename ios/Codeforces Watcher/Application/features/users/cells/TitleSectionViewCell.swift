import SwiftUI

struct TitleSectionViewCell: View {
    
    var title: String
    
    var body: some View {
        CommonText(title)
            .font(.midHeaderSemibold2)
            .foregroundColor(Palette.black.swiftUIColor)
            .padding(.horizontal, 20)
            .padding(.top, 15)
            .padding(.bottom, 5)
            .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct TitleSectionViewCell_Previews: PreviewProvider {
    static var previews: some View {
        TitleSectionViewCell(title: "Section")
    }
}
