import SwiftUI

struct RestorePasswordView: View {
    
    @State var email = ""
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(alignment: .leading, spacing: 40) {
                Text("Restore\npassword")
                    .font(.textHeading)
                
                VStack(alignment: .leading, spacing: 36) {
                    Text("You will get an email with instructions for account recovery")
                        .font(.textBody2)
                    
                    TextInputLayoutView(
                        text: $email,
                        hint: "Email",
                        placeholder: "Email",
                        contentType: .email,
                        tag: 0
                    )
                }
            }
            
            Spacer()
                .frame(height: 72)
            
            Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                Text("Restore password")
                    .font(.textBody)
                    .foregroundColor(.white)
                    .frame(width: 250, height: 40)
                    .background(Palette.black.swiftUIColor)
                    .cornerRadius(30)
            })
            
            Spacer()
            Spacer()
            
            Button(action: {}, label: {
                Text("Lost access to mail?")
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
