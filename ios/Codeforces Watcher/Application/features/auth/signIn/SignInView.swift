import SwiftUI

struct SignInView: View {
    var didSignInClick: ((String, String) -> Void)?
    var onForgotPasswordTap: ((String) -> Void)?
    var didSignUpClick: (() -> Void)?
    
    @State var email = ""
    @State var emailView = ""
    @State var password = ""
    @State var passwordView = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
            
            VStack(alignment: .leading, spacing: 44) {
                Text("Sign In")
                    .font(.system(size: 40, design: .monospaced))
                
                VStack(alignment: .leading, spacing: 24) {
                    TextInputLayoutView(
                        textReal: $email,
                        textView: $emailView,
                        hint: "Email",
                        placeholder: "Email",
                        contentType: .email,
                        tag: 0
                    )
                    
                    TextInputLayoutView(
                        textReal: $password,
                        textView: $passwordView,
                        hint: "Password",
                        placeholder: "Password",
                        contentType: .password,
                        tag: 1
                    )
                }
                .frame(maxWidth: .infinity)
            }
            
            Spacer()
            
            VStack(spacing: 60) {
                Button(action: {
                    self.didSignInClick?(email, password)
                }, label: {
                    Text("Sign in".uppercased())
                        .font(.system(size: 16, design: .monospaced))
                        .foregroundColor(.white)
                        .frame(width: 250, height: 40)
                        .background(Color.black)
                        .cornerRadius(30)
                })
                
                Button(action: {
                    self.onForgotPasswordTap?(email)
                }, label: {
                    Text("Forgot password?")
                        .font(.system(size: 14, design: .monospaced))
                        .foregroundColor(.black)
                })
            }
            
            Spacer()
            Spacer()
            
            HStack {
                Text("Don't have an account yet?")
                    .foregroundColor(.gray)
                
                Button(action: {
                    self.didSignUpClick?()
                }, label: {
                    Text("Sign Up")
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
