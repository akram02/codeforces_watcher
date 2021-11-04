import SwiftUI

class UserTableViewCellNew: UITableViewCell {

    var vc = UIHostingController(rootView: UserTableViewCellView())
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        vc.run {
            contentView.addSubview($0.view)
            
            $0.view.translatesAutoresizingMaskIntoConstraints = false
            $0.view.topAnchor.constraint(equalTo: contentView.topAnchor).isActive = true
            $0.view.rightAnchor.constraint(equalTo: contentView.rightAnchor).isActive = true
            $0.view.bottomAnchor.constraint(equalTo: contentView.bottomAnchor).isActive = true
            $0.view.leftAnchor.constraint(equalTo: contentView.leftAnchor).isActive = true
            $0.view.backgroundColor = .clear

            selectionStyle = .default
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func bind(_ user: UserItem.UserItem) {
        vc.rootView.userHandle = NSMutableAttributedString(attributedString: user.handleText)
        vc.rootView.userRating = NSMutableAttributedString(attributedString: user.ratingText)
        vc.rootView.dateOfLastRatingUpdate = user.ratingUpdateDateText
        vc.rootView.valueOfLastRatingUpdate = NSMutableAttributedString(attributedString: user.ratingUpdateValueText)
    }
}

fileprivate extension UserItem.UserItem {
    
    private var blank: NSAttributedString {
        return NSAttributedString(string: "".localized)
    }
    
    var handleText: NSAttributedString {
        return colorTextByUserRank(text: handle, rank: rank)
    }
    
    var ratingText: NSAttributedString {
        if let rating = rating {
            return colorTextByUserRank(text: "\(rating)", rank: rank)
        } else {
            return blank
        }
    }
    
    var ratingUpdateDateText: String {
        if let ratingChange = ratingChanges.last {
            return "last_rating_update".localizedFormat(args: Double(ratingChange.ratingUpdateTimeSeconds).secondsToUserUpdateDateString())
        } else {
            return "no_rating_update".localized
        }
    }
    
    var ratingUpdateValueText: NSAttributedString {
        if let ratingChange = ratingChanges.last {
            let delta = ratingChange.newRating - ratingChange.oldRating
            let isRatingIncreased = delta >= 0
            let ratingUpdateString = (isRatingIncreased ? "▲" : "▼") + " \(abs(delta))"

            return ratingUpdateString.colorString(color: isRatingIncreased ? Palette.brightGreen : Palette.red)
        } else {
            return blank
        }
    }
}
