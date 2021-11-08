import SwiftUI

struct UserTableViewCellView: View {
    
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
                borderColor: getColorByUserRank(userRank).swiftUIColor
            )
            
            Spacer()
                .frame(width: 8)
            
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    AttributedTextView(
                        attributedString: userHandle,
                        font: UIFont.monospacedSystemFont(ofSize: 16, weight: .semibold),
                        alignment: .center
                    )
                    .fixedSize()

                    Spacer()

                    AttributedTextView(
                        attributedString: userRating,
                        font: UIFont.monospacedSystemFont(ofSize: 16, weight: .semibold),
                        alignment: .center
                    )
                    .fixedSize()
                }

                HStack(spacing: 0) {
                    Text(dateOfLastRatingUpdate)
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .lineLimit(1)

                    Spacer()

                    AttributedTextView(
                        attributedString: valueOfLastRatingUpdate,
                        font: UIFont.monospacedSystemFont(ofSize: 13, weight: .regular),
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

struct UserTableViewCellView_Previews: PreviewProvider {
    static var previews: some View {
        UserTableViewCellView()
    }
}
