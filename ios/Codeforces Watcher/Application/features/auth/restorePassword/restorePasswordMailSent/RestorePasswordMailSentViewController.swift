import SwiftUI

class RestorePasswordMailSentViewController: UIHostingController<RestorePasswordMailSentView> {
    
    init() {
        super.init(rootView: RestorePasswordMailSentView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupComponents()
        setupInteractions()
    }
    
    private func setupComponents() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(closeViewController)
        )
    }
    
    private func setupInteractions() {
        rootView.onOpenMail = {
            guard let mailURL = URL(string: "message://") else { return }
            if UIApplication.shared.canOpenURL(mailURL) {
                UIApplication.shared.open(mailURL)
            }
        }
        
        rootView.onBackSignIn = {
            self.navigationController?.pushViewController(SignInViewController(), animated: true)
        }
    }
    
    @objc func closeViewController() {
        self.navigationController?.popViewController(animated: true)
    }
}
