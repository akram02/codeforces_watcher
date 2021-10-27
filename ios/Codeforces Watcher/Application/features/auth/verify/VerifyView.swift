import SwiftUI

struct VerifyView: View {
    
    var onVerify: (String) -> Void = { _ in }
    
    @State var codeforcesHandle = ""
    
    var verificationCode = ""
    var message = ""
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            Spacer()
                .frame(height: 76)
            
            Text("verify_codeforces_account".localized)
                .font(.bigHeader)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Spacer()
                .frame(height: 44)
            
            VStack(spacing: 0) {
                TextInputLayoutView(
                    text: $codeforcesHandle,
                    hint: "codeforces_handle".localized,
                    placeholder: "codeforces_handle".localized,
                    contentType: .text,
                    tag: 0
                )
                
                Spacer()
                    .frame(height: 24)
                
                VStack(spacing: 0) {
                    AttributedTextView(
                        attributedText: "verify_instruction".localized.attributed,
                        attributeTags: [.bold],
                        defaultFont: UIFont.monospacedSystemFont(ofSize: 14, weight: .regular),
                        defaultTextColor: Palette.darkGray
                    )
                        .frame(height: 88)
                    
                    Spacer()
                        .frame(height: 12)
                    
                    Text(verificationCode)
                        .font(.midHeader)
                    
                    Spacer()
                        .frame(height: 20)
                    
                    Text("verify_change_it_back".localized)
                        .font(.primary2)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .multilineTextAlignment(.center)
                }
            }
            
            Text(message.localized)
                .font(.primarySemibold)
                .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
                .frame(height: 72)
            
            Button(action: {
                self.onVerify(codeforcesHandle)
            }, label: {
                ButtonTextDefault(text: "verify".localized.uppercased())
            })
            
            Spacer()
                .frame(height: 40)
        }
        .padding(.horizontal)
    }
}

struct VerifyView_Previews: PreviewProvider {
    static var previews: some View {
        VerifyView()
    }
}
