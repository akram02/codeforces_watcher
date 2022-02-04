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
        updateFabButton()
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
    
    private func updateFabButton() {
        switch rootView.selectedIndex {
        case 0:
            rootView.fabButtonIconName = "eyeIcon"
            rootView.onFabButton = { [weak self] in
                let viewController = self?.controllers[0] as! ContestsViewController
                viewController.onFabButton()
            }
        case 1:
            rootView.fabButtonIconName = "plusIcon"
            rootView.onFabButton = { [weak self] in
                let viewController = self?.controllers[1] as! UsersViewController
                viewController.onFabButton()
            }
        case 2:
            rootView.fabButtonIconName = "newsShareIcon"
            rootView.onFabButton = { [weak self] in
                let viewController = self?.controllers[2] as! NewsViewController
                viewController.onFabButton()
            }
        case 3:
            rootView.fabButtonIconName = store.state.problems.isFavourite ? "starProblemsIcon" : "infinityIcon"
            rootView.onFabButton = { [weak self] in
                let viewController = self?.controllers[3] as! ProblemsViewController
                viewController.onFabButton()
                self?.updateFabButton()
            }
        default:
            break
        }
    }
}
