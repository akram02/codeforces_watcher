import SwiftUI
import common

class ContestTableViewCellNew: UITableViewCell {

    var cell = UIHostingController(rootView: ContestViewTableViewCell())
    
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

            selectionStyle = .default
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func bind(_ contest: Contest, completion: @escaping (() -> ())) {}
}
