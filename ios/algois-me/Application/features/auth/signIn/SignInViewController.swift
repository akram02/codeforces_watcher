import SwiftUI
import common
import PKHUD

class SignInViewController: UIHostingController<SignInView>, ReKampStoreSubscriber {
    
    init() {
        super.init(rootView: SignInView())
        
        setComponents()
        setInteractions()
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
        
        updateMessage(state.signInMessage)
        
        switch (state.status) {
        case .done:
            hideLoading()
            closeViewController()
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
    
    private func setComponents() {
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(closeViewController)
        )
    }
    
    private func setInteractions() {
        rootView.onSignIn = { email, password in
            store.dispatch(action: AuthRequests.SignIn(email: email, password: password))
        }
        
        rootView.onForgotPassword = {
            self.presentModal(RestorePasswordViewController(dismissCallback: { self.dismiss(animated: true) }))
            analyticsControler.logEvent(eventName: AnalyticsEvents().SIGN_UP_OPENED, params: [:])
        }
        
        rootView.onSignUp = {
            self.presentModal(SignUpViewController())
            analyticsControler.logEvent(eventName: AnalyticsEvents().SIGN_UP_OPENED, params: [:])
        }
    }
    
    func updateMessage(_ message: String?) {
        rootView.message = message ?? ""
    }
    
    func resetMessage() {
        store.dispatch(action: AuthRequests.ResetSignInMessage())
    }

    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
