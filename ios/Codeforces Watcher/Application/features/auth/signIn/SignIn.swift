import SwiftUI

struct SignIn: View {
    @State var email: String
    @State var password: String
    @State var isFocusedEmail = false
    
    var body: some View {
        VStack(spacing: 0) {
            HStack {
                Button(action: {}, label: {
                    Image("back_arrow")
                })
                
                Spacer()
            }
            
            Spacer()
            
            VStack(alignment: .leading, spacing: 44) {
                Text("Sign In")
                    .font(.system(size: 40, design: .monospaced))
                
                VStack(spacing: 24) {
                    VStack(alignment: .leading, spacing: 0) {
                        Text("Email")
                            .foregroundColor(.gray)
                            .font(.system(size: 13, design: .monospaced))
                        
                        TextField("Email", text: Binding.constant(email))
                            .keyboardType(.emailAddress)
                        
                        Divider()
                            .frame(height: 1)
                            .background(Color.black)
                    }
                    
                    VStack(alignment: .leading, spacing: 0) {
                        Text("Password")
                            .foregroundColor(.gray)
                            .font(.system(size: 13, design: .monospaced))
                        
                        SecureField("Password", text: Binding.constant(password))
                            .font(.system(size: 16, design: .monospaced))
                        
                        Divider()
                            .frame(height: 1)
                            .background(Color.black)
                    }
                }
            }
            
            Spacer()
            
            VStack(spacing: 60) {
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    Text("Sign in".uppercased())
                        .font(.system(size: 16, design: .monospaced))
                        .foregroundColor(.white)
                        .frame(width: 250, height: 40)
                        .background(Color.black)
                        .cornerRadius(30)
                })
                
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
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
                
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
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

struct SignIn_Previews: PreviewProvider {
    static var previews: some View {
        SignIn(email: "",
            password: ""
        )
    }
}
