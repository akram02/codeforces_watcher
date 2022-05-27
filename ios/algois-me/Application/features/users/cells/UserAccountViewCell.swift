import SwiftUI

struct UserAccountViewCell: View {
    
    var user: UIModel
    
    var onViewProfile: (String) -> Void = { _ in }
    
    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            VStack(alignment: .leading, spacing: 0) {
                UserCommonInfo(
                    avatar: user.avatar,
                    rank: user.rank!,
                    rating: user.ratingText,
                    maxRating: user.maxRatingText,
                    contribution: user.contributionText
                )
                
                Spacer()
                    .frame(height: 2)
                
                UserInfo(handle: user.handle, name: user.nameText)
                
                Spacer()
                    .frame(height: 12)
                
                HStack {
                    UserRank(rank: user.rankText)
                    
                    Spacer()
                    
                    CommonSmallButton(
                        label: "view_profile".localized,
                        action: {
                            self.onViewProfile(user.handleText.string)
                        },
                        foregroundColor: Palette.black.swiftUIColor,
                        backgroundColor: Color.clear,
                        borderColor: Palette.black.swiftUIColor,
                        borderWidth: 1.6
                    )
                }
            }
            .padding(12)
            .background(Palette.accentGrayish.swiftUIColor)
            .frame(maxWidth: .infinity)
            .cornerRadius(20)
            .padding([.horizontal, .top], 20)
            
            CommonText(user.lastUpdateText)
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
    }
    
    @ViewBuilder
    private func UserCommonInfo(
        avatar: String,
        rank: String,
        rating: NSMutableAttributedString,
        maxRating: NSMutableAttributedString,
        contribution: NSMutableAttributedString
    ) -> some View {
        HStack(spacing: 0) {
            CircleImageViewNew(
                userAvatar: avatar,
                borderColor: getColorByUserRank(rank).swiftUIColor,
                size: (80, 80)
            )
            
            Spacer()
                .frame(width: 18)
            
            VStack(alignment: .leading, spacing: 4) {
                PropertyData(imageName: "ratingIconNew", attributedText: rating)
                PropertyData(imageName: "maxRatingIconNew", attributedText: maxRating)
                PropertyData(imageName: "starIconNew", attributedText: contribution)
            }
        }
    }
    
    @ViewBuilder
    private func PropertyData(imageName: String, attributedText: NSMutableAttributedString) -> some View {
        HStack(spacing: 4) {
            Image(imageName)
                .frame(width: 14, alignment: .center)
            
            AttributedTextView(
                attributedString: attributedText,
                font: Font.monospacedHintRegular,
                alignment: .left
            )
            .fixedSize()
        }
    }
    
    @ViewBuilder
    private func UserInfo(handle: String, name: String) -> some View {
        VStack(alignment: .leading, spacing: 2) {
            CommonText(handle)
                .font(.subHeaderMedium2)
                .foregroundColor(Palette.black.swiftUIColor)
            
            CommonText(name)
                .font(.hintSemibold)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
    }

    private func UserRank(rank: NSMutableAttributedString) -> some View {
        let rankColor = getColorByUserRank(rank.string.lowercased())
        
        return AttributedTextView(
            attributedString: rank,
            font: Font.monospacedHeaderMedium,
            alignment: .left
        )
        .fixedSize()
        .shadow(color: rankColor.lighter(by: 0.2, alpha: 0.5)?.swiftUIColor ?? Color.clear, radius: 8, x: 0, y: 0)
        .shadow(color: rankColor.lighter(by: 0.1)?.swiftUIColor ?? Color.clear, radius: 12, x: 0, y: 0)
    }
    
    struct UIModel {
        var avatar: String
        var handle: String
        var rating: Int?
        var maxRating: Int?
        var rank: String?
        var maxRank: String?
        var contribution: Int64
        var firstName: String?
        var lastName: String?
        var lastUpdate: Int64?
        
        init(_ user: UserItem.UserAccountItem) {
            avatar = user.avatar
            handle = user.handle
            rating = user.rating
            maxRating = user.maxRating
            rank = user.rank
            maxRank = user.maxRank
            contribution = user.contribution
            firstName = user.firstName
            lastName = user.lastName
            lastUpdate = user.ratingChanges.last?.ratingUpdateTimeSeconds
        }
    }
}

fileprivate extension UserAccountViewCell.UIModel {
    
    private var none: NSMutableAttributedString {
        return NSMutableAttributedString(string: "None".localized)
    }
    
    var handleText: NSMutableAttributedString {
        return colorTextByUserRank(text: handle, rank: rank)
    }
    
    var rankText: NSMutableAttributedString {
        if let rank = rank {
            return colorTextByUserRank(text: rank.capitalized, rank: rank)
        } else {
            return none
        }
    }
    
    var ratingText: NSMutableAttributedString {
        if let rating = rating {
            return colorRating(text: "rating".localizedFormat(args: "\(rating)"), rating: rating, rank: rank)
        } else {
            return none
        }
    }
    
    var maxRatingText: NSMutableAttributedString {
        if let rating = maxRating {
            return colorRating(text: "max_rating".localizedFormat(args: "\(rating)"), rating: maxRating, rank: maxRank)
        } else {
            return none
        }
    }
    
    private func colorRating(text: String, rating: Int?, rank: String?) -> NSMutableAttributedString {
        var attributedText = NSMutableAttributedString(string: text)

        let color = getColorByUserRank(rank)
        
        if let rating = rating {
            let tag = "\(rating)"
            if let range = text.firstOccurrence(string: tag) {
                attributedText.colored(with: color, range: range)
            }
            
            attributedText = colorPropertyName(attributedText)
        }
        
        return attributedText
    }
    
    var contributionText: NSMutableAttributedString {
        let contributionSubstring = (contribution <= 0 ? "\(contribution)" : "+\(contribution)")
        return colorContribution(text: "Contribution".localizedFormat(args: contributionSubstring), contributionSubstring)
    }
    
    private func colorContribution(text: String, _ contributionSubstring: String) -> NSMutableAttributedString {
        var attributedText = NSMutableAttributedString(string: text)
       
        if let range = text.firstOccurrence(string: contributionSubstring) {
            let colorOfContribution = (contribution >= 0 ? Palette.green : Palette.red)
            
            attributedText.colored(with: colorOfContribution, range: range)
        }
        
        attributedText = colorPropertyName(attributedText)

        return attributedText
    }
    
    private func colorPropertyName(_ attributedText: NSMutableAttributedString) -> NSMutableAttributedString {
        let text = attributedText.string
        
        if let index = text.firstIndex(of: ":") {
            let range = NSMakeRange(0, text.distance(from: text.startIndex, to: index) + 1)
            attributedText.colored(with: Palette.black, range: range)
        }
        
        return attributedText
    }
    
    var nameText: String {
        let name = "\(firstName ?? "") \(lastName ?? "")"
        if name != "" {
            return name
        } else {
            return none.string
        }
    }

    var lastUpdateText: String {
        if let lastUpdate = lastUpdate {
            return "rating_updated_on".localizedFormat(args: Double(lastUpdate).secondsToUserUpdateDateString())
        } else {
            return "never_updated".localized
        }
    }
}
