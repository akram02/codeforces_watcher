import SwiftUI

struct PostTextView: View {
    
    var content: String
    var authorAvatar: String
    var authorHandle: NSAttributedString
    var authorRankColor: CGColor
    var title: String
    var agoText: String
    
    var body: some View {
        VStack(spacing: 12) {
            PostTitleView(
                authorAvatar: authorAvatar,
                authorHandle: authorHandle,
                authorRankColor: authorRankColor,
                title: title,
                agoText: agoText
            )
            
            CommonText(content)
                .font(.hintRegular)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(4)
        }
        .frame(maxWidth: .infinity)
        .padding(12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}
