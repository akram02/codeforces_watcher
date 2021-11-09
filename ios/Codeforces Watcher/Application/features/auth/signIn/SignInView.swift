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
                    .font(.bigHeader)
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
            
            ErrorMessage(message: message)
            
            VStack(spacing: 72) {
                Button(action: {
                    self.onSignIn(email, password)
                }, label: {
                    ButtonTextDefault(text: "sign_in".localized.uppercased())
                })
                
                Button(action: {
                    self.onForgotPassword()
                }, label: {
                    Text("forgot_password".localized)
                        .underline()
                        .font(.hintSemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                Text("sign_up_hint".localized)
                    .font(.primary2)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                
                Button(action: {
                    self.onSignUp()
                }, label: {
                    Text("sign_up".localized)
                        .underline()
                        .font(.primary2)
                        .fontWeight(.semibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
            .lineLimit(1)
        }
        .padding()
    }
}

struct SignInView_Previews: PreviewProvider {
    static var previews: some View {
        SignInView()
    }
}
