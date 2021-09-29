import SwiftUI

struct SignInView: View {
    
    var onSignIn: ((String, String) -> Void)?
    var onForgotPassword: ((String) -> Void)?
    var onSignUp: (() -> Void)?
    
    @State var email = ""
    @State var password = ""
    
    var error: String = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            
            VStack(alignment: .leading, spacing: 44) {
                Text("sign_in".localized)
                    .font(.system(size: 40, design: .monospaced))
                
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
            
            Text(error.localized)
                .font(.system(size: 16, weight: .semibold, design: .monospaced))
                .shadow(color: .red, radius: 8, x: 0, y: 0)
                .frame(height: 80)
            
            VStack(spacing: 60) {
                Button(action: {
                    self.onSignIn?(email, password)
                }, label: {
                    Text("sign_in".localized.uppercased())
                        .font(.system(size: 16, design: .monospaced))
                        .foregroundColor(.white)
                        .frame(width: 250, height: 40)
                        .background(Color.black)
                        .cornerRadius(30)
                })
                
                Button(action: {
                    self.onForgotPassword?(email)
                }, label: {
                    Text("forgot_password".localized)
                        .font(.system(size: 14, design: .monospaced))
                        .foregroundColor(.black)
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                Text("sign_up_hint".localized)
                    .foregroundColor(.gray)
                
                Button(action: {
                    self.onSignUp?()
                }, label: {
                    Text("sign_up".localized)
                        .foregroundColor(.black)
                        .underline()
                })
            }
            .lineLimit(1)
            .font(.system(size: 14, design: .monospaced))
        }
        .padding()
    }
}

struct SignInView_Previews: PreviewProvider {
    static var previews: some View {
        SignInView()
    }
}
