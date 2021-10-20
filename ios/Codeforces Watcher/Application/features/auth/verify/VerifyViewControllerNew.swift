import SwiftUI

class VerifyViewControllerNew: UIHostingController<VerifyView> {
    
    init() {
        super.init(rootView: VerifyView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setNavigationBar()
    }
    
    private func setNavigationBar() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(closeViewController)
        )
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}

