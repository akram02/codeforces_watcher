import SwiftUI

struct VerifyView: View {
    
    @State var codeforcesHandle = ""
    
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
                    VerifyInstructionLabelView(text: "verify_instruction".localized.attributed)
                        .frame(height: 88)
                    
                    Spacer()
                        .frame(height: 12)
                    
                    Text("xV123GH5")
                        .font(.midHeader)
                    
                    Spacer()
                        .frame(height: 20)
                    
                    Text("verify_change_it_back".localized)
                        .font(.primary2)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .multilineTextAlignment(.center)
                }
            }
            
            Spacer()
                .frame(height: 72)
            
            Button(action: {}, label: {
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
