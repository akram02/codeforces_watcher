import SwiftUI

class SignUpViewControllerNew: UIHostingController<SignUpView> {
    
    init() {
        super.init(rootView: SignUpView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setNavigationBar()
        setInteractions()
    }
    
    private func setNavigationBar() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(closeViewController)
        )
    }
    
    private func setInteractions() {
        rootView.onLink = { link in
            self.presentModal(WebViewController(link, ""))
        }
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
