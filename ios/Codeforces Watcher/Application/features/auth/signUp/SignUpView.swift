import SwiftUI

struct SignUpView: View {
    
    var onSignUp: (String, String, String) -> Void = { _, _, _ in }
    var onSignIn: () -> Void = {}
    var onLink: (String) -> Void = { _ in }
    
    @State var email = ""
    @State var password = ""
    @State var confirmPassword = ""
    
    @State var isAgreementChecked = false
    @State var agreementHeight: CGFloat = 0
    
    var message = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            Text("sign_up".localized)
                .font(.bigHeader)
                .foregroundColor(Palette.black.swiftUIColor)
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
            
            VStack(spacing: 0) {
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
                
                Text(message)
                    .font(.primarySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
                    .frame(height: 60)
                
                Button(action: {
                    self.onSignUp(email, password, confirmPassword)
                }, label: {
                    if isAgreementChecked {
                        ButtonTextDefault(text: "sign_up".localized.uppercased())
                    } else {
                        ButtonTextInverse(text: "sign_up".localized.uppercased())
                    }
                })
                .disabled(!isAgreementChecked)
            }
            
            Spacer()
                
            HStack {
                Text("sign_in_hint".localized)
                    .foregroundColor(Palette.darkGray.swiftUIColor)

                Button(action: {
                    self.onSignIn()
                }, label: {
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
