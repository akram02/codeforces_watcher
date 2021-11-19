import SwiftUI

struct UserAccountViewCell: View {
    
    var avatar: String = ""
    var rank: NSMutableAttributedString = "".attributed
    
    var handle: String = ""
    var name: String = ""
    var rating: NSMutableAttributedString = "".attributed
    var maxRating: NSMutableAttributedString = "".attributed
    var contribution: NSMutableAttributedString = "".attributed
    var dateOfLastRatingUpdate: String = "".localized
    
    var onViewProfile: (String) -> Void = { _ in }
    
    var rankColor: UIColor {
        getColorByUserRank(rank.string.lowercased())
    }
    
    var body: some View {
        VStack(alignment: .center, spacing: 6) {
            VStack(alignment: .leading, spacing: 0) {
                HStack(spacing: 0) {
                    CircleImageViewNew(
                        userAvatar: avatar,
                        borderColor: getColorByUserRank(rank.string).swiftUIColor,
                        size: (80, 80)
                    )
                    
                    Spacer()
                        .frame(width: 18)
                    
                    HStack(spacing: 6) {
                        VStack(spacing: 0) {
                            Image("ratingIconNew")
                            
                            Spacer()
                                .frame(height: 10)
                            
                            Image("maxRatingIconNew")
                            
                            Spacer()
                                .frame(height: 8)
                            
                            Image("starIconNew")
                        }
                        
                        VStack(alignment: .leading, spacing: 0) {
                            AttributedTextView(
                                attributedString: rating,
                                font: Font.monospacedHintRegular,
                                alignment: .left
                            )
                            .fixedSize()
                            
                            Spacer()
                                .frame(height: 4)
                            
                            AttributedTextView(
                                attributedString: maxRating,
                                font: Font.monospacedHintRegular,
                                alignment: .left
                            )
                            .fixedSize()
                            
                            Spacer()
                                .frame(height: 5)
                            
                            AttributedTextView(
                                attributedString: contribution,
                                font: Font.monospacedHintRegular,
                                alignment: .left
                            )
                            .fixedSize()
                        }
                    }
                }
                
                Spacer()
                    .frame(height: 2)
                
                VStack(alignment: .leading, spacing: 2) {
                    CommonText(handle)
                        .font(.subHeaderMedium2)
                        .foregroundColor(Palette.black.swiftUIColor)
                    
                    CommonText(name)
                        .font(.hintSemibold)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                }
                
                Spacer()
                    .frame(height: 12)
                
                HStack {
                    AttributedTextView(
                        attributedString: rank,
                        font: Font.monospacedHeaderMedium,
                        alignment: .left
                    )
                    .fixedSize()
                    .shadow(color: rankColor.lighter(by: 0.2, alpha: 0.5)?.swiftUIColor ?? Color.clear, radius: 8, x: 0, y: 0)
                    .shadow(color: rankColor.lighter(by: 0.1)?.swiftUIColor ?? Color.clear, radius: 12, x: 0, y: 0)
                    
                    Spacer()
                    
                    SmallCommonButton(
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
            .cornerRadius(20)
            .padding([.horizontal, .top], 20)
            
            CommonText(dateOfLastRatingUpdate)
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
        }
    }
}

struct UserAccountViewCell_Previews: PreviewProvider {
    static var previews: some View {
        UserAccountViewCell()
    }
}
