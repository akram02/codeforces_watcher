import SwiftUI
import common

class UsersViewControllerNew: UIHostingController<UsersView>, ReKampStoreSubscriber {
    
    private lazy var fabButton = FabButtonViewController(name: "plusIcon")
    private var followedUsers: [User] = []
    
    init() {
        super.init(rootView: UsersView())
        
        setInteractions()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
        setFabButton()
        fabButton.show()
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                KotlinBoolean(bool: oldState.users == newState.users && oldState.auth == newState.auth)
            }.select { state in
                state
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        fabButton.hide()
        
        store.unsubscribe(subscriber: self)
    }
    
    private func setFabButton() {
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
    }
    
    private func onFabButton() { }
    
    private func setInteractions() {
        rootView.onUserAccount = { handle in
            self.presentModal(UserViewController(handle, isUserAccount: true))
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! AppState
        
        let userState = state.users
        let authState = state.auth
        
        followedUsers = userState.followedUsers
        let sortedUsers = sortUsers(userState.sortType)
        
        switch authState.authStage {
        case .notSignedIn:
            let onLogin = {
                self.presentModal(SignInViewController())
                analyticsControler.logEvent(eventName: AnalyticsEvents().SIGN_IN_OPENED, params: [:])
            }
            rootView.users =
                [.loginItem(onLogin)] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        case .signedIn:
            let onVerify = {
                self.presentModal(VerifyViewController())
                analyticsControler.logEvent(eventName: AnalyticsEvents().VERIFY_OPENED, params: [:])
            }
            rootView.users =
                [.verifyItem(onVerify)] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        case .verified:
            guard let codeforcesUser = userState.userAccount?.codeforcesUser else { fatalError() }
            rootView.users =
                [.userAccount(UserItem.UserAccountItem(codeforcesUser))] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        default:
            break
        }
    }
    
    private func sortUsers(_ sortType: UsersState.SortType) -> [User] {
        var sortedUsers = followedUsers
        
        switch(sortType) {
        case .default_:
            sortedUsers.reverse()
        case .ratingDown:
            sortedUsers.sort(by: {
                $0.rating?.intValue ?? Int.min < $1.rating?.intValue ?? Int.min
            })
        case .ratingUp:
            sortedUsers.sort(by: {
                $0.rating?.intValue ?? Int.min > $1.rating?.intValue ?? Int.min
            })
        case .updateDown:
            sortedUsers.sort(by: {
                $0.ratingChanges.last?.ratingUpdateTimeSeconds ?? Int64.min < $1.ratingChanges.last?.ratingUpdateTimeSeconds ?? Int64.min
            })
        case .updateUp:
            sortedUsers.sort(by: {
                $0.ratingChanges.last?.ratingUpdateTimeSeconds ?? Int64.min > $1.ratingChanges.last?.ratingUpdateTimeSeconds ?? Int64.min
            })
        default:
            break
        }
        
        return sortedUsers
    }
}

fileprivate extension Array where Element == User {
    func mapToItems() -> [UserItem] {
        compactMap { user in
            UserItem.userItem(UserItem.UserItem(user))
        }
    }
}
