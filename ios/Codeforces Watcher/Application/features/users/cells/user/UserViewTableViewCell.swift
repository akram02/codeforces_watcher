import SwiftUI

struct UserViewTableViewCell: View {
    
    var userAvatar: String = ""
    var userRank: String? = nil
    
    var userHandle: NSMutableAttributedString = "".attributed
    var userRating: NSMutableAttributedString = "".attributed
    var dateOfLastRatingUpdate: String = ""
    var valueOfLastRatingUpdate: NSMutableAttributedString = "".attributed
    
    var body: some View {
        HStack(spacing: 0) {
            CircleImageViewNew(
                userAvatar: userAvatar,
                borderColor: getColorByUserRank(userRank).swiftUIColor,
                size: (36, 36)
            )
            
            Spacer()
                .frame(width: 8)
            
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    AttributedTextView(
                        attributedString: userHandle,
                        font: Font.monospacedBodySemibold,
                        alignment: .center
                    )
                    .fixedSize()

                    Spacer()

                    AttributedTextView(
                        attributedString: userRating,
                        font: Font.monospacedBodySemibold,
                        alignment: .center
                    )
                    .fixedSize()
                }

                HStack(spacing: 0) {
                    CommonText(dateOfLastRatingUpdate)
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .lineLimit(1)

                    Spacer()

                    AttributedTextView(
                        attributedString: valueOfLastRatingUpdate,
                        font: Font.monospacedHintRegular,
                        alignment: .center
                    )
                    .fixedSize()
                }
            }
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
}

struct UserViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        UserViewTableViewCell()
    }
}
