import SwiftUI

struct LoginViewTableViewCell: View {
    
    var onLogin: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading, spacing: 2) {
            Image("avatar")
            
            CommonText("Who are you?")
                .font(.subHeaderMedium2)
                .foregroundColor(Palette.black.swiftUIColor)
            
            
            HStack(alignment: .bottom, spacing: 0) {
                CommonText("Login to identify and get instant push notifications about rating updates")
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                
                Spacer()
                    .frame(width: 48)
                
                SmallCommonButton(
                    label: "login".uppercased(),
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
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding(20)
    }
}

struct LoginViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        LoginViewTableViewCell()
    }
}
