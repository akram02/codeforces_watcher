import SwiftUI

class UserAccountTableViewCell: UITableViewCell {

    var cell = UIHostingController(rootView: UserAccountViewCell())
    
    private var user: UserItem.UserAccountItem?
    
    var onViewProfile: (String) -> Void = { _ in }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        cell.run {
            contentView.addSubview($0.view)
            
            $0.view.translatesAutoresizingMaskIntoConstraints = false
            $0.view.topAnchor.constraint(equalTo: contentView.topAnchor).isActive = true
            $0.view.rightAnchor.constraint(equalTo: contentView.rightAnchor).isActive = true
            $0.view.bottomAnchor.constraint(equalTo: contentView.bottomAnchor).isActive = true
            $0.view.leftAnchor.constraint(equalTo: contentView.leftAnchor).isActive = true
            $0.view.backgroundColor = .clear

            selectionStyle = .none
        }
        
        setInteractions()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    struct UIModel {
        let avatar: String
        let handle: String
        let rating: Int?
        let maxRating: Int?
        let rank: String?
        let maxRank: String?
        let contribution: Int64
        let firstName: String?
        let lastName: String?
        let lastUpdate: Int64?
    }
    
    func bind(_ user: UserItem.UserAccountItem, onUserAccountTap: @escaping (String) -> Void) {
        let uiModel = UserAccountTableViewCell.UIModel(
            avatar: user.avatar,
            handle: user.handle,
            rating: user.rating,
            maxRating: user.maxRating,
            rank: user.rank,
            maxRank: user.maxRank,
            contribution: user.contribution,
            firstName: user.firstName,
            lastName: user.lastName,
            lastUpdate: user.ratingChanges.last?.ratingUpdateTimeSeconds
        )
        
        cell.rootView.avatar = user.avatar
        cell.rootView.handle = uiModel.handle
        cell.rootView.name = uiModel.nameText
        cell.rootView.rating = uiModel.ratingText as! NSMutableAttributedString
        cell.rootView.maxRating = uiModel.maxRatingText as! NSMutableAttributedString
        cell.rootView.rank = uiModel.rankText as! NSMutableAttributedString
        cell.rootView.contribution = uiModel.contributionText as! NSMutableAttributedString
        cell.rootView.dateOfLastRatingUpdate = uiModel.lastUpdateText
        
        self.onViewProfile = onUserAccountTap
    }
    
    private func setInteractions() {
        cell.rootView.onViewProfile = { handle in
            self.onViewProfile(handle)
        }
    }
}

fileprivate extension UserAccountTableViewCell.UIModel {
    
    private var none: NSAttributedString {
        return NSAttributedString(string: "None".localized)
    }
    
    var handleText: NSMutableAttributedString {
        return colorTextByUserRank(text: handle, rank: rank)
    }
    
    var rankText: NSAttributedString {
        if let rank = rank {
            return colorTextByUserRank(text: rank.capitalized, rank: rank)
        } else {
            return none
        }
    }
    
    var ratingText: NSAttributedString {
        if let rating = rating {
            return colorRating(text: "rating".localizedFormat(args: "\(rating)"), rating: rating, rank: rank)
        } else {
            return none
        }
    }
    
    var maxRatingText: NSAttributedString {
        if let rating = maxRating {
            return colorRating(text: "max_rating".localizedFormat(args: "\(rating)"), rating: maxRating, rank: maxRank)
        } else {
            return none
        }
    }
    
    private func colorRating(text: String, rating: Int?, rank: String?) -> NSAttributedString {
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
    
    var contributionText: NSAttributedString {
        let contributionSubstring = (contribution <= 0 ? "\(contribution)" : "+\(contribution)")
        return colorContribution(text: "Contribution".localizedFormat(args: contributionSubstring), contributionSubstring)
    }
    
    private func colorContribution(text: String, _ contributionSubstring: String) -> NSAttributedString {
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
