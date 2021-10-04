import SwiftUI
import common
import PKHUD

class RestorePasswordViewController: UIHostingController<RestorePasswordView>, ReKampStoreSubscriber {
    
    init() {
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
        
        store.unsubscribe(subscriber: self)
    }
    
    func onNewState(state: Any) {
        let state = state as! AuthState
        
        switch (state.status) {
        case .done:
            hideLoading()
            showMessage(state.restorePasswordMessage)
            self.presentModal(UIHostingController(rootView: RestorePasswordMailSentView()))
        case .pending:
            showLoading()
        case .idle:
            hideLoading()
            showMessage(state.restorePasswordMessage)
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
        rootView.onRestorePassword = { email in
            store.dispatch(action: AuthRequests.SendPasswordReset(email: email))
        }
    }
    
    func showMessage(_ message: String) {
        rootView.message = message
    }

    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
