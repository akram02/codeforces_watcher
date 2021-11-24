import SwiftUI

class LoginTableViewCell: UITableViewCell {

    var cell = UIHostingController(rootView: LoginViewTableViewCell())
    
    var onLogin: () -> Void = {}
    
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
    
    func bind(onLogin: @escaping () -> Void) {
        self.onLogin = onLogin
    }
    
    private func setInteractions() {
        cell.rootView.onLogin = {
            self.onLogin()
        }
    }
}
