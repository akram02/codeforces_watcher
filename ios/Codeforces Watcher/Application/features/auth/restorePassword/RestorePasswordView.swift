import SwiftUI

struct RestorePasswordView: View {
    
    var onRestorePassword: (String) -> Void = {_ in }
    
    @State var email = ""
    
    var message = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 56)
            
            VStack(alignment: .leading, spacing: 40) {
                Text("restore_password".localized)
                    .font(.bigHeader)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                VStack(alignment: .leading, spacing: 36) {
                    Text("restore_password_hint".localized)
                        .font(.primary2)
                        .foregroundColor(Palette.black.swiftUIColor)
                    
                    TextInputLayoutView(
                        text: $email,
                        hint: "email".localized,
                        placeholder: "email".localized,
                        contentType: .email,
                        tag: 0
                    )
                }
            }
            
            ErrorMessage(message: message)
            
            Button(action: {
                self.onRestorePassword(email)
            }, label: {
                ButtonTextDefault(text: "restore_password_button".localized)
            })
            
            Spacer()
            Spacer()
            
            Button(action: {}, label: {
                Text("lost_access".localized)
                    .underline()
                    .font(.primarySemibold)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
            })
            .hidden()
        }
        .padding([.horizontal, .bottom], 20)
    }
}

struct RestorePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordView()
    }
}
