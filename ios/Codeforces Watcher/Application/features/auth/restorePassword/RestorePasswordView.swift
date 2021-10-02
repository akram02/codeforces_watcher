import SwiftUI

struct RestorePasswordView: View {
    
    @State var email = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(alignment: .leading, spacing: 40) {
                Text("restore_password".localized)
                    .font(.textHeading)
                
                VStack(alignment: .leading, spacing: 36) {
                    Text("restore_password_hint".localized)
                        .font(.textBody2)
                    
                    TextInputLayoutView(
                        text: $email,
                        hint: "email".localized,
                        placeholder: "email".localized,
                        contentType: .email,
                        tag: 0
                    )
                }
            }
            
            Spacer()
                .frame(height: 72)
            
            Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                Text("restore_password_button".localized)
                    .font(.textBody)
                    .foregroundColor(.white)
                    .frame(width: 250, height: 40)
                    .background(Palette.black.swiftUIColor)
                    .cornerRadius(30)
            })
            
            Spacer()
            Spacer()
            
            Button(action: {}, label: {
                Text("lost_access".localized)
                    .font(.textBody2)
                    .underline()
                    .foregroundColor(Palette.gray.swiftUIColor)
            })
        }
        .padding()
    }
}

struct RestorePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordView()
    }
}
