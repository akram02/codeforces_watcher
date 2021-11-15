import SwiftUI

struct SignUpView: View {
    
    var onSignUp: (String, String, String, Bool) -> Void = { _, _, _, _ in }
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
                .frame(height: 56)
            
            Text("sign_up".localized)
                .font(.bigHeaderMedium)
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
                    })
                    
                    AttributedTextView(
                        attributedString: "agreement_terms_and_privacy".localized.attributed,
                        attributeTags: [.term, .privacy],
                        font: Font.monospacedHintRegular,
                        foregroundColor: Palette.black,
                        alignment: .left,
                        height: $agreementHeight,
                        onLink: { link in
                            self.onLink(link)
                        }
                    )
                    .frame(height: agreementHeight)
                }
                .padding(.horizontal, 20)
                
                ErrorMessageView(message: message)
                
                Button(action: {
                    self.onSignUp(email, password, confirmPassword, isAgreementChecked)
                }, label: {
                    if isAgreementChecked {
                        BigButtonLabel(
                            label: "sign_up".localized.uppercased(),
                            isInverted: false
                        )
                    } else {
                        BigButtonLabel(
                            label: "sign_up".localized.uppercased(),
                            isInverted: true
                        )
                    }
                })
            }
            
            Spacer()
                
            HStack {
                Text("sign_in_hint".localized)
                    .font(.bodyRegular2)
                    .foregroundColor(Palette.darkGray.swiftUIColor)

                Button(action: {
                    self.onSignIn()
                }, label: {
                    Text("sign_in".localized)
                        .font(.bodySemibold2)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .underline()
                })
            }
        }
        .padding([.horizontal, .bottom], 20)
    }
}

struct SignUpView_Previews: PreviewProvider {
    static var previews: some View {
        SignUpView()
    }
}
