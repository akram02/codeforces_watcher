import SwiftUI

struct SignInView: View {
    
    var onSignIn: (String, String) -> Void = {_, _ in }
    var onForgotPassword: () -> Void = {}
    var onSignUp: () -> Void = {}
    
    @State var email = ""
    @State var password = ""
    
    var message: String = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 56)
            
            VStack(alignment: .leading, spacing: 44) {
                CommonText("sign_in".localized)
                    .font(.bigHeaderMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                VStack(alignment: .leading, spacing: 24) {
                    TextInputLayoutView(
                        text: $email,
                        hint: "email".localized,
                        placeholder: "email".localized,
                        contentType: .email,
                        tag: 0
                    )
                    
                    TextInputLayoutView(
                        text: $password,
                        hint: "password".localized,
                        placeholder: "password".localized,
                        contentType: .password,
                        tag: 1
                    )
                }
            }
            
            ErrorMessageView(message: message)
            
            VStack(spacing: 72) {
                CommonBigButton(
                    label: "sign_in".localized.uppercased(),
                    action: {
                        self.onSignIn(email, password)
                    },
                    isInverted: false
                )
                
                Button(action: {
                    self.onForgotPassword()
                }, label: {
                    CommonText("forgot_password".localized, underlined: true)
                        .font(.hintSemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                CommonText("sign_up_hint".localized)
                    .font(.bodyRegular2)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                
                Button(action: {
                    self.onSignUp()
                }, label: {
                    CommonText("sign_up".localized, underlined: true)
                        .font(.bodySemibold2)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
            .lineLimit(1)
        }
        .padding([.horizontal, .bottom], 20)
    }
}

struct SignInView_Previews: PreviewProvider {
    static var previews: some View {
        SignInView()
    }
}
