import SwiftUI

struct SignInView: View {
    //var dismissAction: (() -> Void)?
    
    @State var email = ""
    @State var password = ""
    
    var body: some View {
        VStack(spacing: 0) {
            /*HStack {
                Button(action: {
                    //dismissAction?()
                }, label: {
                    Image("back_arrow")
                })
                
                Spacer()
            }*/
            
            Spacer()
            
            VStack(alignment: .leading, spacing: 44) {
                Text("Sign In")
                    .font(.system(size: 40, design: .monospaced))
                
                VStack(alignment: .leading, spacing: 24) {
                    TextInputLayoutView(
                        text: $email,
                        hint: "Email",
                        placeholder: "Email",
                        contentType: .email,
                        tag: 0
                    )
                    
                    TextInputLayoutView(
                        text: $password,
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
        //.navigationBarHidden(true)
    }
}

struct SignInView_Previews: PreviewProvider {
    static var previews: some View {
        SignInView()
    }
}
