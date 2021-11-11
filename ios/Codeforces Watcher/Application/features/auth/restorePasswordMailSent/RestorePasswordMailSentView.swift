import SwiftUI

struct RestorePasswordMailSentView: View {
    
    var onOpenMail: () -> Void = {}
    var onBackSignIn: () -> Void = {}
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 56)
            
            VStack(spacing: 12) {
                Text("check_your_box".localized)
                    .font(.subHeader)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                Text("open_mail_hint".localized)
                    .font(.header)
                    .foregroundColor(Palette.black.swiftUIColor)
            }
            
            Spacer()
                .frame(height: 72)
            
            Image("email")
                .resizable()
                .frame(width: 108, height: 80)
            
            Spacer()
                .frame(height: 72)
            
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
        .padding([.horizontal, .bottom], 20)
    }
}

struct RestorePasswordMailSentView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordMailSentView()
    }
}
