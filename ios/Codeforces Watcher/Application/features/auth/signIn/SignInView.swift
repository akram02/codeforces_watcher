import SwiftUI

struct SignInView: View {
    
    var onSignIn: ((String, String) -> Void)?
    var onForgotPassword: (() -> Void)?
    var onSignUp: (() -> Void)?
    
    @State var email = ""
    @State var password = ""
    
    var error: String = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(alignment: .leading, spacing: 44) {
                Text("sign_in".localized)
                    .font(.textHeading)
                
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
            
            Text(error)
                .font(.textBody)
                .bold()
                .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
                .frame(height: 72)
            
            VStack(spacing: 60) {
                Button(action: {
                    self.onSignIn?(email, password)
                }, label: {
                    Text("sign_in".localized.uppercased())
                        .font(.textBody)
                        .foregroundColor(.white)
                        .frame(width: 250, height: 40)
                        .background(Palette.black.swiftUIColor)
                        .cornerRadius(30)
                })
                
                Button(action: {
                    self.onForgotPassword?()
                }, label: {
                    Text("forgot_password".localized)
                        .font(.textBody2)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                Text("sign_up_hint".localized)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                
                Button(action: {
                    self.onSignUp?()
                }, label: {
                    Text("sign_up".localized)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .underline()
                })
            }
            .font(.textBody2)
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
