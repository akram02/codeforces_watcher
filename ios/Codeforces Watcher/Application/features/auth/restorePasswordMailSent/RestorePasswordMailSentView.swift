import SwiftUI

struct RestorePasswordMailSentView: View {
    
    var onOpenMail: () -> Void = {}
    var onBackSignIn: () -> Void = {}
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                ScrollView(showsIndicators: false) {
                    Spacer()
                        .frame(height: 56)
                    
                    VStack(spacing: 12) {
                        CommonText("check_your_box".localized)
                            .font(.subHeaderMedium)
                            .foregroundColor(Palette.black.swiftUIColor)
                        
                        CommonText("open_mail_hint".localized)
                            .font(.headerMedium)
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
                        CommonBigButton(
                            label: "open_mail".localized,
                            action: {
                                self.onOpenMail()
                            }, isInverted: false
                        )
                        
                        CommonBigButton(
                            label: "back_to_sign_in".localized,
                            action: {
                                self.onBackSignIn()
                            }, isInverted: true
                        )
                    }
                }
                
                CommonText("check_your_spam_folder".localized)
                    .font(.bodyRegular2)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
            }
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
