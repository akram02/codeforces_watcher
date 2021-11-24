import SwiftUI

struct UserAccountViewCell: View {
    
    var avatar: String = ""
    
    var handle: String = ""
    var name: String = ""
    var rating: NSMutableAttributedString = "".attributed
    var maxRating: NSMutableAttributedString = "".attributed
    var rank: NSMutableAttributedString = "".attributed
    var contribution: NSMutableAttributedString = "".attributed
    var dateOfLastRatingUpdate: String = "".localized
    
    var onViewProfile: (String) -> Void = { _ in }
    
    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            VStack(alignment: .leading, spacing: 0) {
                UserCommonInfo(
                    avatar: avatar,
                    rank: rank.string,
                    rating: rating,
                    maxRating: maxRating,
                    contribution: contribution
                )
                
                Spacer()
                    .frame(height: 2)
                
                UserInfo(handle: handle, name: name)
                
                Spacer()
                    .frame(height: 12)
                
                HStack {
                    UserRank(rank: rank)
                    
                    Spacer()
                    
                    CommonSmallButton(
                        label: "View profile",
                        action: {
                            self.onViewProfile(handle)
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
            
            CommonText(dateOfLastRatingUpdate)
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
    }
    
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
}

struct UserAccountViewCell_Previews: PreviewProvider {
    static var previews: some View {
        UserAccountViewCell()
    }
}
