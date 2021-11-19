import SwiftUI
import common
import PKHUD

class RestorePasswordViewController: UIHostingController<RestorePasswordView>, ReKampStoreSubscriber {
    
    let dismissCallback: () -> Void
    
    init(dismissCallback: @escaping () -> Void) {
        self.dismissCallback = dismissCallback
        
        super.init(rootView: RestorePasswordView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                return KotlinBoolean(bool: oldState.auth == newState.auth)
            }.select { state in
                return state.auth
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        resetMessage()
        
        store.unsubscribe(subscriber: self)
    }
    
    func onNewState(state: Any) {
        let state = state as! AuthState
        
        updateMessage(state.restorePasswordMessage)
        
        switch (state.status) {
        case .done:
            hideLoading()
            self.presentModal(RestorePasswordMailSentViewController(dismissCallback: dismissCallback))
        case .pending:
            showLoading()
        case .idle:
            hideLoading()
        default:
            return
        }
    }
    
    private func showLoading() {
        PKHUD.sharedHUD.userInteractionOnUnderlyingViewsEnabled = false
        HUD.show(.progress, onView: UIApplication.shared.windows.last)
    }
    
    private func hideLoading() {
        HUD.hide(afterDelay: 0)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setComponents()
        setInteractions()
    }
    
    private func setComponents() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(closeViewController)
        )
    }
    
    private func setInteractions() {
        rootView.onRestorePassword = { email in
            store.dispatch(action: AuthRequests.SendPasswordReset(email: email))
        }
    }
    
    func updateMessage(_ message: String?) {
        rootView.message = message ?? ""
    }
    
    func resetMessage() {
        store.dispatch(action: AuthRequests.ResetRestorePasswordMessage())
    }

    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
