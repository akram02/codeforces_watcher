import SwiftUI

class FabButtonViewController: UIHostingController<FabButtonView> {
    
    init() {
        super.init(rootView: FabButtonView())
        
        self.view.isHidden = false
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setView() {
        self.view.run {
            $0.topToSuperview(offset: -22)
            $0.centerXToSuperview()
            $0.isHidden = false
        }
    }
    
    func setImage(name: String) {
        rootView.name = name
    }
    
    func hide() {
        self.view.isHidden = true
    }
}
