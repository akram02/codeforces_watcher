import SwiftUI
import common
import PKHUD

class SignInViewControllerNew: UIHostingController<SignInView>, ReKampStoreSubscriber {
    init() {
        super.init(rootView: SignInView())
    }
    
    @objc required dynamic init?(coder aDecoder: NSCoder) {
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
    
    @objc(onNewStateState:) func onNewState(state: Any) {
        let state = state as! AuthState
        
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
        rootView.didSignInClick = { email, password in
            store.dispatch(action: AuthRequests.SignIn(email: email, password: password))
        }
        
        rootView.onForgotPasswordTap = { email in
            store.dispatch(action: AuthRequests.SendPasswordReset(email: email))
        }
        
        rootView.didSignUpClick = {
            self.presentModal(SignUpViewController())
            analyticsControler.logEvent(eventName: AnalyticsEvents().SIGN_UP_OPENED, params: [:])
        }
    }

    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
