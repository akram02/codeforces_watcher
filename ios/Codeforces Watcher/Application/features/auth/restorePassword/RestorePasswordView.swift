import SwiftUI

struct RestorePasswordView: View {
    
    var onRestorePassword: ((String) -> Void)?
    
    @State var email = ""
    
    var message = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(alignment: .leading, spacing: 40) {
                Text("restore_password".localized)
                    .font(.heading)
                
                VStack(alignment: .leading, spacing: 36) {
                    Text("restore_password_hint".localized)
                        .font(.body2)
                    
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
                .font(.body)
                .bold()
                .shadow(color: Palette.red.swiftUIColor, radius: 8, x: 0, y: 0)
                .frame(height: 72)
            
            Button(action: {
                self.onRestorePassword?(email)
            }, label: {
                Text("restore_password_button".localized)
                    .font(.body)
                    .foregroundColor(.white)
                    .frame(width: 250, height: 40)
                    .background(Palette.black.swiftUIColor)
                    .cornerRadius(30)
            })
            
            Spacer()
            Spacer()
            
            Button(action: {}, label: {
                Text("lost_access".localized)
                    .font(.body2)
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
