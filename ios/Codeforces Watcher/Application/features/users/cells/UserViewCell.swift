import SwiftUI

struct UserViewCell: View {
    
    var user: UserItem.UserItem
    
    var body: some View {
        HStack(spacing: 0) {
            CircleImageViewNew(
                userAvatar: user.avatar,
                borderColor: getColorByUserRank(user.rank).swiftUIColor,
                size: (36, 36)
            )
            
            Spacer()
                .frame(width: 8)
            
            VStack(spacing: 0) {
                HStack(spacing: 0) {
                    AttributedTextView(
                        attributedString: user.handleText,
                        font: Font.monospacedBodySemibold,
                        alignment: .center
                    )
                    .fixedSize()

                    Spacer()

                    AttributedTextView(
                        attributedString: user.ratingText,
                        font: Font.monospacedBodySemibold,
                        alignment: .center
                    )
                    .fixedSize()
                }

                HStack(spacing: 0) {
                    CommonText(user.ratingUpdateDateText)
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .lineLimit(1)

                    Spacer()

                    AttributedTextView(
                        attributedString: user.ratingUpdateValueText,
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

fileprivate extension UserItem.UserItem {
    
    private var blank: NSMutableAttributedString {
        return NSMutableAttributedString(string: "".localized)
    }
    
    var handleText: NSMutableAttributedString {
        return colorTextByUserRank(text: handle, rank: rank)
    }
    
    var ratingText: NSMutableAttributedString {
        if let rating = rating {
            return colorTextByUserRank(text: "\(rating)", rank: rank)
        } else {
            return blank
        }
    }
    
    var ratingUpdateDateText: String {
        if let ratingChange = ratingChanges.last {
            return "last_activity".localizedFormat(args: Double(ratingChange.ratingUpdateTimeSeconds).secondsToUserUpdateDateString())
        } else {
            return "no_activity".localized
        }
    }
    
    var ratingUpdateValueText: NSMutableAttributedString {
        if let ratingChange = ratingChanges.last {
            let delta = ratingChange.newRating - ratingChange.oldRating
            let isRatingIncreased = delta >= 0
            let ratingUpdateString = (isRatingIncreased ? "▲" : "▼") + " \(abs(delta))"

            return ratingUpdateString.colorString(color: isRatingIncreased ? Palette.brightGreen : Palette.red) as! NSMutableAttributedString
        } else {
            return blank
        }
    }
}
