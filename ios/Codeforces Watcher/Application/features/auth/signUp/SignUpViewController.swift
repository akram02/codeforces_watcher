import SwiftUI
import common
import PKHUD

class SignUpViewController: UIHostingController<SignUpView>, ReKampStoreSubscriber {
    
    init() {
        super.init(rootView: SignUpView())
        
        setNavigationBar()
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
        
        updateMessage(state.signUpMessage)
        
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
    
    private func updateMessage(_ message: String?) {
        rootView.message = message ?? ""
    }
    
    private func resetMessage() {
        store.dispatch(action: AuthRequests.ResetSignUpMessage())
    }
    
    private func showLoading() {
        PKHUD.sharedHUD.userInteractionOnUnderlyingViewsEnabled = false
        HUD.show(.progress, onView: UIApplication.shared.windows.last)
    }
    
    private func hideLoading() {
        HUD.hide(afterDelay: 0)
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
        rootView.onSignUp = { email, password, confirmPassword, isAgreementChecked in
            store.dispatch(
                action: AuthRequests.SignUp(
                    email: email,
                    password: password,
                    confirmPassword: confirmPassword,
                    isPrivacyPolicyAccepted: isAgreementChecked
                )
            )
        }
        
        rootView.onSignIn = {
            self.closeViewController()
        }
        
        rootView.onLink = { link in
            self.presentModal(WebViewController(link, ""))
        }
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
