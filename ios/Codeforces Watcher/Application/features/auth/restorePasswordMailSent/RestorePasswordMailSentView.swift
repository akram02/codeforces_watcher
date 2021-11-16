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
                    .font(.subHeaderMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .kerning(-1)
                
                Text("open_mail_hint".localized)
                    .font(.headerMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .kerning(-1)
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
                    BigButtonLabel(
                        label: "open_mail".localized,
                        isInverted: false
                    )
                })
                
                Button(action: {
                    self.onBackSignIn()
                }, label: {
                    BigButtonLabel(
                        label: "back_to_sign_in".localized,
                        isInverted: true
                    )
                })
            }
            
            Spacer()
            Spacer()
            
            Text("check_your_spam_folder".localized)
                .font(.bodyRegular2)
                .foregroundColor(Palette.darkGray.swiftUIColor)
                .kerning(-1)
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
