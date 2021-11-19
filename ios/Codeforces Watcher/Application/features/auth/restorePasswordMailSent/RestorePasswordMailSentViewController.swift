import SwiftUI

class RestorePasswordMailSentViewController: UIHostingController<RestorePasswordMailSentView> {
    
    let dismissCallback: () -> Void
    
    init(dismissCallback: @escaping () -> Void) {
        self.dismissCallback = dismissCallback
        
        super.init(rootView: RestorePasswordMailSentView())
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
        rootView.onOpenMail = {
            guard let mailURL = URL(string: "message://") else { return }
            
            if UIApplication.shared.canOpenURL(mailURL) {
                UIApplication.shared.open(mailURL)
            } else {
                let toastHandler = IOSToastHandler()
                toastHandler.showToast(message: "mail_app_not_found".localized)
            }
        }
        
        rootView.onBackSignIn = {
            self.dismissCallback()
        }
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
