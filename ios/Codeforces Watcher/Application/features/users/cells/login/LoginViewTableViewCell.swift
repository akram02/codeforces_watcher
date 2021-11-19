import SwiftUI

struct LoginViewTableViewCell: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Image("avatar")
            
            CommonText("Who are you?")
                .font(.subHeaderMedium2)
                .foregroundColor(Palette.black.swiftUIColor)
            
            
            HStack {
                GeometryReader { geometry in
                    CommonText("Login to identify and get instant push notifications about rating updates")
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .frame(width: geometry.size.width * 0.8, alignment: .leading)
                }
                
                Spacer()
                
                SmallCommonButton(
                    label: "login".uppercased(),
                    action: {},
                    foregroundColor: Palette.black.swiftUIColor,
                    backgroundColor: Color.clear,
                    borderColor: Palette.black.swiftUIColor,
                    borderWidth: 1.6
                )
            }
        }
        .padding(12)
        .frame(height: 190)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding([.horizontal, .top], 20)
    }
}

struct LoginViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        LoginViewTableViewCell()
    }
}
