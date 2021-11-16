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
                Text("sign_in".localized)
                    .font(.bigHeaderMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .kerning(-1)
                
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
                Button(action: {
                    self.onSignIn(email, password)
                }, label: {
                    BigButtonLabel(
                        label: "sign_in".localized.uppercased(),
                        isInverted: false
                    )
                })
                
                Button(action: {
                    self.onForgotPassword()
                }, label: {
                    Text("forgot_password".localized)
                        .font(.hintSemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .kerning(-1)
                        .underline()
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                Text("sign_up_hint".localized)
                    .font(.bodyRegular2)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                    .kerning(-1)
                
                Button(action: {
                    self.onSignUp()
                }, label: {
                    Text("sign_up".localized)
                        .font(.bodySemibold2)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .kerning(-1)
                        .underline()
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
