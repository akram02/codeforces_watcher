import SwiftUI

struct SignUpView: View {
    
    @State var email = ""
    @State var password = ""
    @State var confirmPassword = ""
    
    @State var isAgreementChecked = false
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            Text("Sign Up")
                .font(.bigHeader)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Spacer()
                .frame(height: 44)
            
            VStack(spacing: 24) {
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
                
                TextInputLayoutView(
                    text: $confirmPassword,
                    hint: "Confirm password",
                    placeholder: "Confirm password",
                    contentType: .password,
                    tag: 2
                )
            }
            
            Spacer()
                .frame(height: 36)
            
            VStack(spacing: 60) {
                HStack(spacing: 12) {
                    Button(action: {
                        self.isAgreementChecked.toggle()
                    }, label: {
                        let imageName = isAgreementChecked ? "ic_checkbox_checked" : "ic_checkbox_unchecked"
                        
                        Image(imageName)
                            .resizable()
                            .frame(width: 18, height: 18)
                    })
                    
                    Text("I agree with the Terms and Conditions and the Privacy Policy")
                        .font(.hintRegular)
                }
                .padding(.horizontal)
                
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    if isAgreementChecked {
                        ButtonTextDefault(text: "SIGN UP")
                    } else {
                        ButtonTextInverse(text: "SIGN UP")
                    }
                })
            }
            
            Spacer()
                
            HStack {
                Text("Already have an account?")
                    .foregroundColor(Palette.darkGray.swiftUIColor)

                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    Text("Sign In")
                        .fontWeight(.semibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .underline()
                })
            }
            .font(.primary2)
            .padding(.bottom)
        }
        .padding(.horizontal)
    }
}

struct SignUpView_Previews: PreviewProvider {
    static var previews: some View {
        SignUpView()
    }
}
