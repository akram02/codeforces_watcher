import SwiftUI

struct ProblemViewTableViewCell: View {
    
    @State var isFavourite = false
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 0) {
                CommonText("1264A: Feeding Chicken")
                    .font(.bodySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                CommonText("Codeforces Round #601 (Div. 1)")
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
            }
            .lineLimit(1)
            .frame(maxWidth: .infinity, alignment: .leading)
            
            Button(action: {
                isFavourite.toggle()
            }, label: {
                Image("starIconNew")
                    .resizable()
                    .renderingMode(.template)
                    .frame(width: 20, height: 20)
                    .foregroundColor(isFavourite ?
                                        Palette.colorAccent.swiftUIColor : Palette.black.swiftUIColor)
            })
        }
        .padding([.horizontal, .top], 20)
    }
}

struct ProblemViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        ProblemViewTableViewCell()
    }
}
