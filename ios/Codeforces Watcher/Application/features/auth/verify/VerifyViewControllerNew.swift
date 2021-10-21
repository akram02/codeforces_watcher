import SwiftUI
import common
import PKHUD

class VerifyViewControllerNew: UIHostingController<VerifyView>, ReKampStoreSubscriber {
    
    init() {
        super.init(rootView: VerifyView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                KotlinBoolean(bool: oldState.verification == newState.verification)
            }.select { state in
                state.verification
            }
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! VerificationState
        
        switch (state.status) {
        case .idle:
            hideLoading()
        case .pending:
            showLoading()
        case .done:
            hideLoading()
            closeViewController()
        default:
            return
        }
        
        rootView.verificationCode = state.verificationCode ?? ""
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        fetchVerificationCode()
        setNavigationBar()
        
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
    
    private func fetchVerificationCode() {
        store.dispatch(action: VerificationRequests.FetchVerificationCode())
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}

