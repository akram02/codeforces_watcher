import SwiftUI

struct SignUpView: View {
    
    @State var email = ""
    @State var password = ""
    @State var confirmPassword = ""
    
    @State var isAgreementChecked = false
    @State var agreementHeight: CGFloat = 0
    
    var onLink: (String) -> Void = { _ in }
    
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
                
                TextInputLayoutView(
                    text: $confirmPassword,
                    hint: "confirm_password".localized,
                    placeholder: "confirm_password".localized,
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
                    
                    AttributedTextView(
                        attributedString: "agreement_terms_and_privacy".localized.attributed,
                        attributeTags: [.term, .privacy],
                        font: UIFont.monospacedSystemFont(ofSize: 13, weight: .regular),
                        foregroundColor: Palette.black,
                        height: $agreementHeight,
                        onLink: { link in
                            self.onLink(link)
                        }
                    )
                    .frame(height: agreementHeight)
                }
                .padding(.horizontal)
                
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    if isAgreementChecked {
                        ButtonTextDefault(text: "sign_up".localized.uppercased())
                    } else {
                        ButtonTextInverse(text: "sign_up".localized.uppercased())
                    }
                })
            }
            
            Spacer()
                
            HStack {
                Text("sign_in_hint".localized)
                    .foregroundColor(Palette.darkGray.swiftUIColor)

                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    Text("sign_in".localized)
                        .font(.primary2)
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
