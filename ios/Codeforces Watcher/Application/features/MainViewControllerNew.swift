import SwiftUI

class MainViewControllerNew: UIHostingController<MainView> {
    
    init() {
        super.init(rootView: MainView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
