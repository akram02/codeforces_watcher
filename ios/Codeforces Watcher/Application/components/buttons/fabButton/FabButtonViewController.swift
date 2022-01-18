import SwiftUI

class FabButtonViewController: UIHostingController<FabButtonView> {
    
    var action: () -> Void = {}
    
    init(name: String) {
        super.init(rootView: FabButtonView(name: name))
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func setView() {
        self.view.run {
            $0.topToSuperview(offset: -16)
            $0.centerXToSuperview()
            $0.backgroundColor = .clear
        }
        
        setInteractions()
    }
    
    func setButtonAction(action: @escaping () -> Void) {
        self.action = action
    }
    
    func updateImage(name: String) {
        rootView.name = name
    }
    
    func show() {
        self.view.isHidden = false
    }
    
    func hide() {
        self.removeFromParent()
        self.view.isHidden = true
    }
    
    private func setInteractions() {
        rootView.action = {
            self.action()
        }
    }
}
