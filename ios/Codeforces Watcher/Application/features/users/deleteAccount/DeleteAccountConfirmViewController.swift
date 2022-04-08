import SwiftUI

class DeleteAccountConfirmViewController: UIHostingController<DeleteAccountConfirmView> {
    
    init() {
        super.init(rootView: DeleteAccountConfirmView())
        
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
        
        rootView.onDeleteAccount = { [weak self] isAccepted in
            if (isAccepted) {
                
            } else {
                self?.showToast(message: "mark_checkbox".localized)
            }
        }
    }
    
    private func showToast(message: String) {
        let toastHandler = IOSToastHandler()
        toastHandler.showToast(message: message)
    }
}
