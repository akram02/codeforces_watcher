import SwiftUI

class FabButtonViewController: UIHostingController<FabButtonView> {
    
    var action: () -> Void = {}
    
    init() {
        super.init(rootView: FabButtonView())
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
    
    func setButton(name: String, action: @escaping () -> Void) {
        updateImage(name: name)
        self.action = action
    }
    
    func updateImage(name: String) {
        rootView.name = name
    }
    
    func show() {
        self.view.isHidden = false
    }
    
    func hide() {
        self.view.isHidden = true
    }
    
    private func setInteractions() {
        rootView.action = {
            self.action()
        }
    }
}
