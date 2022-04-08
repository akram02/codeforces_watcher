import SwiftUI

class DeleteAccountViewController: UIHostingController<DeleteAccountView> {
    
    init() {
        super.init(rootView: DeleteAccountView())
        
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
    }
    
    private func setInteractions() {
        rootView.onDismiss = { [weak self] in
            self?.dismiss(animated: true)
        }
        
        rootView.onDeleteAccount = { [weak self] in
            self?.presentModal(DeleteAccountConfirmViewController())
        }
    }
}
