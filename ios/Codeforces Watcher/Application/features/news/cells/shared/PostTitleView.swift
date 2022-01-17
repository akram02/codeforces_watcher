import SwiftUI

struct PostTitleView: View {
    
    var authorAvatar: String
    var authorHandle: NSAttributedString
    var authorRankColor: CGColor
    var title: String
    var agoText: String
    
    var body: some View {
        HStack(spacing: 8) {
            CircleImageViewNew(
                userAvatar: authorAvatar,
                borderColor: Color(authorRankColor),
                size: (width: 36, height: 36)
            )
            
            VStack(alignment: .leading, spacing: 0) {
                CommonText(title)
                    .font(.bodySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .lineLimit(1)
                
                PostDetail
            }
            .frame(maxWidth: .infinity)
        }
    }
    
    private var PostDetail: some View {
        HStack(spacing: 0) {
            AttributedTextView(
                attributedString: authorHandle as! NSMutableAttributedString,
                font: Font.monospacedHintRegular,
                alignment: .left
            )
                .fixedSize()
            
            CommonText(agoText)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
        .font(.hintRegular)
        .frame(maxWidth: .infinity, alignment: .leading)
    }
}
