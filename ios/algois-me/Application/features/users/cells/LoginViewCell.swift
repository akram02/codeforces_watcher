import SwiftUI

struct LoginViewCell: View {
    
    var onLogin: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading, spacing: 2) {
            Image("avatar")
            
            CommonText("who_are_you".localized)
                .font(.subHeaderMedium2)
                .foregroundColor(Palette.black.swiftUIColor)
            
            HStack(alignment: .bottom, spacing: 0) {
                CommonText("login_to_identify".localized)
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                    .lineLimit(3)
                
                Spacer()
                
                CommonSmallButton(
                    label: "login".localized.uppercased(),
                    action: {
                        self.onLogin()
                    },
                    foregroundColor: Palette.black.swiftUIColor,
                    backgroundColor: Color.clear,
                    borderColor: Palette.black.swiftUIColor,
                    borderWidth: 1.6
                )
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding(20)
    }
}

struct LoginViewCell_Previews: PreviewProvider {
    static var previews: some View {
        LoginViewCell()
    }
}
