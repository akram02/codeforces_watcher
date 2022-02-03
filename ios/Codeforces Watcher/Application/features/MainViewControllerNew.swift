import SwiftUI

class MainViewControllerNew: UIHostingController<MainView> {
    
    private let controllers = [
        ContestsViewController(),
        UsersViewController(),
        NewsViewController(),
        ProblemsViewController()
    ]
    
    init() {
        super.init(rootView: MainView(container: ViewControllerContainer(viewController: self.controllers[0])))
        
        setContainerView()
        addChildViewController()
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setContainerView() {
        view.addSubview(rootView.container.uiView)
    }
    
    private func addChildViewController() {
        rootView.container.viewController.run {
            addChild($0)
            $0.didMove(toParent: self)
        }
    }
    
    private func removeChildViewController() {
        rootView.container.viewController.run {
            $0.willMove(toParent: self)
            $0.removeFromParent()
        }
    }

    private func setInteractions() {
        rootView.onTabItem = { [weak self] index in
            guard let childController = self?.controllers[index] else { return }
            
            self?.rootView.selectedIndex = index
            
            self?.removeChildViewController()
            self?.rootView.container.viewController = childController
            self?.addChildViewController()
        }
    }
}
