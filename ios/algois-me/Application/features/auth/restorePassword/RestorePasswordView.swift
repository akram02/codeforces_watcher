import SwiftUI

struct RestorePasswordView: View {
    
    var onRestorePassword: (String) -> Void = {_ in }
    
    @State var email = ""
    
    var message = ""
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            Spacer()
                .frame(height: 56)
            
            VStack(alignment: .leading, spacing: 40) {
                CommonText("restore_password".localized)
                    .font(.bigHeaderMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                VStack(alignment: .leading, spacing: 36) {
                    CommonText("restore_password_hint".localized)
                        .font(.bodyRegular2)
                        .foregroundColor(Palette.black.swiftUIColor)
                    
                    TextInputLayoutView(
                        text: $email,
                        hint: "email".localized,
                        placeholder: "email".localized,
                        contentType: .email,
                        tag: 0
                    )
                }
            }
            
            ErrorMessageView(message: message)
            
            CommonBigButton(
                label: "restore_password_button".localized,
                action: {
                    self.onRestorePassword(email)
                }, isInverted: false
            )
        }
        .padding(.horizontal, 20)
    }
}

struct RestorePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordView()
    }
}
