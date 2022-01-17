import SwiftUI

struct FeedbackViewNew: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            HStack(alignment: .top) {
                CommonText("rate_us_first_title".localized)
                    .font(SwiftUI.Font.system(size: 22, weight: .regular, design: .monospaced))
                    .foregroundColor(Palette.black.swiftUIColor)
                
                Spacer()
                
                Image("crossIconNew")
                    .resizable()
                    .frame(width: 24, height: 24)
            }
            
            HStack(spacing: 20) {
                CommonSmallButton(
                    label: "yes".localized,
                    action: {},
                    foregroundColor: Palette.white.swiftUIColor,
                    backgroundColor: Palette.black.swiftUIColor,
                    borderColor: Palette.white.swiftUIColor,
                    borderWidth: 0
                )
                
                Button(action: {}, label: {
                    CommonText("no_thanks".localized)
                        .font(.hintSemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                })
            }
        }
        .frame(maxWidth: .infinity)
        .padding(16)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}

struct FeedbackViewNew_Previews: PreviewProvider {
    static var previews: some View {
        FeedbackViewNew()
    }
}
