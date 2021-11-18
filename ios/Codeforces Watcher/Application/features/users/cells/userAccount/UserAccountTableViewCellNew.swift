import SwiftUI

class UserAccountTableViewCellNew: UITableViewCell {

    var cell = UIHostingController(rootView: UserAccountViewCell())
    
    private var user: UserItem.UserAccountItem?
    
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
    
    func bind(_ user: UserItem.UserAccountItem) {
        let uiModel = UserAccountTableViewCellNew.UIModel(
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
        cell.rootView.rating = uiModel.ratingText
        cell.rootView.maxRating = uiModel.maxRatingText
        cell.rootView.rank = uiModel.rankText
        cell.rootView.contribution = uiModel.contributionText
        cell.rootView.dateOfLastRatingUpdate = uiModel.lastUpdateText
    }
}

fileprivate extension UserAccountTableViewCellNew.UIModel {
    
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
        let attributedText = NSMutableAttributedString(string: text)

        let color = getColorByUserRank(rank)
        
        if let rating = rating {
            let tag = "\(rating)"
            if let range = text.firstOccurrence(string: tag) {
                attributedText.colored(with: color, range: range)
            }
        }
        
        return attributedText
    }
    
    var contributionText: NSMutableAttributedString {
        let contributionSubstring = (contribution <= 0 ? "\(contribution)" : "+\(contribution)")
        return colorContribution(text: "Contribution".localizedFormat(args: contributionSubstring), contributionSubstring)
    }
    
    private func colorContribution(text: String, _ contributionSubstring: String) -> NSMutableAttributedString {
        let attributedText = NSMutableAttributedString(string: text)
       
        if let range = text.firstOccurrence(string: contributionSubstring) {
            let colorOfContribution = (contribution >= 0 ? Palette.green : Palette.red)
            
            attributedText.colored(with: colorOfContribution, range: range)
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
