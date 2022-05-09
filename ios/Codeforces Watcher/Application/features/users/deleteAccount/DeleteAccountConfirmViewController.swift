import SwiftUI
import PKHUD
import common

class DeleteAccountConfirmViewController: UIHostingController<DeleteAccountConfirmView>, ReKampStoreSubscriber {
    
    private let dismissCallback: () -> Void
    
    init(dismissCallback: @escaping () -> Void) {
        self.dismissCallback = dismissCallback
        
        super.init(rootView: DeleteAccountConfirmView())
        
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                KotlinBoolean(bool: oldState.auth == newState.auth)
            }.select { state in
                state.auth
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        resetMessage()
        
        store.unsubscribe(subscriber: self)
    }
    
    private func setInteractions() {
        rootView.onDismiss = { [weak self] in
            self?.dismiss(animated: true)
        }
        
        rootView.onDeleteAccount = { isAccepted in
            store.dispatch(action: AuthRequests.DeleteAccount(isAccepted: isAccepted))
        }
    }
    
    private func updateMessage(_ message: String?) {
        rootView.message = message ?? ""
    }
    
    private func resetMessage() {
        store.dispatch(action: AuthRequests.ResetDeleteAccountMessage())
    }
    
    private func showLoading() {
        PKHUD.sharedHUD.userInteractionOnUnderlyingViewsEnabled = false
        HUD.show(.progress, onView: UIApplication.shared.windows.last)
    }
    
    private func hideLoading() {
        HUD.hide(afterDelay: 0)
    }
    
    func onNewState(state: Any) {
        let state = state as! AuthState
        
        switch (state.status) {
        case .pending:
            showLoading()
        case .idle:
            hideLoading()
        default:
            return
        }
        
        if state.authStage == .notSignedIn {
            dismissCallback()
        }
        
        updateMessage(state.deleteAccountMessage)
    }
}
