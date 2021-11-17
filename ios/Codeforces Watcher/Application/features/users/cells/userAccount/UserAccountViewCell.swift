import SwiftUI

struct UserAccountViewCell: View {
    var body: some View {
        ZStack {
            VStack(alignment: .leading, spacing: 0) {
                HStack(spacing: 0) {
                    Image("noImage")
                        .resizable()
                        .frame(width: 80, height: 80)
                    
                    Spacer()
                        .frame(width: 18)
                    
                    HStack(spacing: 6) {
                        VStack(spacing: 0) {
                            Image("ratingIconNew")
                            
                            Spacer()
                                .frame(height: 10)
                            
                            Image("maxRatingIconNew")
                            
                            Spacer()
                                .frame(height: 8)
                            
                            Image("starIconNew")
                        }
                        
                        VStack(alignment: .leading, spacing: 0) {
                            Text("Rating: 1658")
                                .kerning(-1)
                            
                            Spacer()
                                .frame(height: 4)
                            
                            Text("Max rating: 1820")
                                .kerning(-1)
                            
                            Spacer()
                                .frame(height: 5)
                            
                            Text("Contribution: +13")
                                .kerning(-1)
                        }
                    }
                    .font(.hintRegular)
                    .foregroundColor(Palette.black.swiftUIColor)
                }
                
                VStack(alignment: .leading, spacing: 0) {
                    Text("@iwanowww")
                        .font(SwiftUI.Font.system(size: 28, weight: .medium, design: .monospaced))
                        .foregroundColor(Palette.black.swiftUIColor)
                        .kerning(-1)
                    
                    Text("Vasilii Ivanov")
                        .font(.hintSemibold)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .kerning(-1)
                }
                
                Spacer()
                    .frame(height: 12)
                
                HStack {
                    Text("Expert")
                        .font(.header)
                        .foregroundColor(Palette.blue.swiftUIColor)
                        .kerning(-1)
                        .shadow(color: Palette.brightBlueberry.swiftUIColor, radius: 8, x: 0, y: 0)
                        .shadow(color: Palette.darkBlueberry.swiftUIColor, radius: 12, x: 0, y: 0)
                    
                    Spacer()
                    
                    Button(action: {}, label: {
                        Text("View profile")
                            .font(.hintSemibold)
                            .foregroundColor(Palette.black.swiftUIColor)
                            .frame(minWidth: 104)
                            .frame(height: 16)
                            .padding(8)
                            .background(Color.clear)
                            .clipShape(
                                 RoundedRectangle(cornerRadius: 30)
                             )
                             .overlay(
                                 RoundedRectangle(cornerRadius: 30)
                                    .strokeBorder(Palette.black.swiftUIColor, lineWidth: 1.6)
                             )
                             .lineLimit(1)
                    })
                }
            }
            .padding(12)
        }
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding(20)
    }
}

struct UserAccountViewCell_Previews: PreviewProvider {
    static var previews: some View {
        UserAccountViewCell()
    }
}
