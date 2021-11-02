import SwiftUI

struct RestorePasswordView: View {
    
    var onRestorePassword: (String) -> Void = {_ in }
    
    @State var email = ""
    
    var message = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
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
            
            Text(message)
                .font(.primarySemibold)
                .foregroundColor(Palette.black.swiftUIColor)
                .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
                .frame(height: 72)
            
            Button(action: {
                self.onRestorePassword(email)
            }, label: {
                ButtonTextDefault(text: "restore_password_button".localized)
            })
            
            Spacer()
            Spacer()
            
            Button(action: {}, label: {
                Text("lost_access".localized)
                    .font(.primarySemibold)
                    .underline()
                    .foregroundColor(Palette.darkGray.swiftUIColor)
            })
            .hidden()
        }
        .padding()
    }
}

struct RestorePasswordView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordView()
    }
}
