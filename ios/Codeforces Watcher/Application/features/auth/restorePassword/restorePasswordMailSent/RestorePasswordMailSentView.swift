import SwiftUI

struct RestorePasswordMailSentView: View {
    
    var onOpenMail: () -> Void = {}
    var onBackSignIn: () -> Void = {}
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(spacing: 12) {
                Text("check_your_box".localized)
                    .font(.subHeader)
                
                Text("open_mail_hint".localized)
                    .font(.header)
            }
            
            Spacer()
                .frame(height: 68)
            
            Image("email")
                .resizable()
                .frame(width: 108, height: 80)
                .scaledToFit()
            
            Spacer()
                .frame(height: 68)
            
            VStack(spacing: 20) {
                Button(action: {
                    self.onOpenMail()
                }, label: {
                    ButtonTextDefault(text: "open_mail".localized)
                })
                
                Button(action: {
                    self.onBackSignIn()
                }, label: {
                    ButtonTextInverse(text: "back_to_sign_in".localized)
                })
            }
            
            Spacer()
            Spacer()
            
            Text("check_your_spam_folder".localized)
                .font(.primary2)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
        .multilineTextAlignment(.center)
        .padding()
    }
}

struct RestorePasswordMailSentView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordMailSentView()
    }
}
