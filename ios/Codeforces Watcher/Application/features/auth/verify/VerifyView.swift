import SwiftUI

struct VerifyView: View {
    
    var onVerify: (String) -> Void = { _ in }
    
    @State var codeforcesHandle = ""
    
    @State var verifyInstructionHeight: CGFloat = 0
    
    var verificationCode = ""
    var message = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 56)
            
            Text("verify_codeforces_account".localized)
                .font(.bigHeaderMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .kerning(-1)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Spacer()
                .frame(height: 44)
            
            VStack(spacing: 24) {
                TextInputLayoutView(
                    text: $codeforcesHandle,
                    hint: "codeforces_handle".localized,
                    placeholder: "codeforces_handle".localized,
                    contentType: .text,
                    tag: 0
                )
                
                VStack(spacing: 0) {
                    AttributedTextView(
                        attributedString: "verify_instruction".localized.attributed,
                        attributeTags: [.bold],
                        font: Font.monospacedBodyRegular2,
                        foregroundColor: Palette.darkGray,
                        alignment: .left,
                        height: $verifyInstructionHeight
                    )
                    .frame(height: verifyInstructionHeight)
                    
                    Spacer()
                        .frame(height: 12)
                    
                    Text(verificationCode)
                        .font(.midHeaderSemibold)
                        .kerning(-1)
                        .foregroundColor(Palette.black.swiftUIColor)
                    
                    Spacer()
                        .frame(height: 20)
                    
                    Text("verify_change_it_back".localized)
                        .font(.bodyRegular2)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .kerning(-1)
                        .multilineTextAlignment(.center)
                }
            }
            
            ErrorMessageView(message: message)
            
            CommonBigButton(
                label: "verify".localized.uppercased(),
                action: {
                    self.onVerify(codeforcesHandle)
                }, isInverted: false
            )
            
            Spacer()
            Spacer()
        }
        .padding(.horizontal, 20)
    }
}

struct VerifyView_Previews: PreviewProvider {
    static var previews: some View {
        VerifyView()
    }
}
