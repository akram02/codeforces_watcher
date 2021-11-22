import SwiftUI

struct ContestViewTableViewCell: View {
    var body: some View {
        VStack {
            HStack(spacing: 8) {
                Image("Codeforces")
                    .resizable()
                    .frame(width: 36, height: 36)
                    .clipShape(Circle())
                
                VStack(spacing: 4) {
                    CommonText("Codeforces Round #745 (Div. 1)")
                        .font(.bodySemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    CommonText("13:05 Sep 30, Thursday")
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .lineLimit(1)
                
                Image("contestsIcon")
                    .frame(width: 18, height: 20)
            }
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
}

struct ContestViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        ContestViewTableViewCell()
    }
}
