import SwiftUI

struct TitleSectionViewTableViewCell: View {
    
    var title = "".localized
    
    var body: some View {
        Text(title)
            .font(.bodyRegular)
            .foregroundColor(Palette.black.swiftUIColor)
            .kerning(-1)
            .padding(.horizontal, 20)
            .padding(.top, 15)
            .padding(.bottom, 5)
            .frame(maxWidth: .infinity, alignment: .leading)
    }
}

struct TitleSectionViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        TitleSectionViewTableViewCell(title: "Section")
    }
}
