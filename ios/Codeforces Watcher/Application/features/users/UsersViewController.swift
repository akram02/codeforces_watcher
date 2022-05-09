import SwiftUI
import PKHUD
import common

class UsersViewController: UIHostingController<UsersView>, ReKampStoreSubscriber {
    
    private var followedUsers: [User] = []
    
    init() {
        super.init(rootView: UsersView())
        
        setInteractions()
        setRefreshControl()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        setPicker()
        hideNavigationBar()
        
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
        
        store.unsubscribe(subscriber: self)
    }
    
    func onFabButton() {
        addUserCardToggle()
    }
    
    private func setInteractions() {
        rootView.onUserAccount = { handle in
            self.presentModal(UserViewController(handle, isUserAccount: true, dismissCallback: { [weak self] in
                self?.dismiss(animated: true)
            }))
        }
        
        rootView.onUser = { handle in
            self.presentModal(UserViewController(handle, isUserAccount: false))
        }
        
        rootView.addUserCardToggle = {
            self.addUserCardToggle()
        }
        
        rootView.onAddUser = { handle in
            store.dispatch(action: UsersRequests.AddUser(handle: handle))
        }
    }
    
    private func setRefreshControl() {
        rootView.refreshControl.run {
            $0.addTarget(self, action: #selector(refreshUsers(_:)), for: .valueChanged)
            $0.tintColor = Palette.black
        }
    }
    
    private func setPicker() {
        rootView.pickerOptions = ["default", "rating_up", "rating_down", "update_up", "update_down"].map {
            $0.localized
        }
        
        rootView.pickerSelectedPosition = Int(store.state.users.sortType.position)
        
        rootView.onOptionSelected = { position in
            let sortType = UsersState.SortTypeCompanion().getSortType(sortType: position)
            store.dispatch(action: UsersActions.Sort(sortType: sortType))
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! AppState
        
        let userState = state.users
        let authState = state.auth
        
        followedUsers = userState.followedUsers
        let sortedUsers = sortUsers(userState.sortType)
        
        if userState.status == .idle {
            rootView.refreshControl.endRefreshing()
        }
        
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
        
        switch userState.addUserStatus {
        case .done:
            hideLoading()
            store.dispatch(action: UsersActions.ClearAddUserState())
        case .pending:
            showLoading()
        case .idle:
            hideLoading()
        default:
            break
        }
    }
    
    private func addUserCardToggle() {
        rootView.isAddUserCardDisplayed.toggle()
        self.tabBarController?.tabBar.items?.forEach { $0.isEnabled = !rootView.isAddUserCardDisplayed }
    }
    
    private func showLoading() {
        PKHUD.sharedHUD.userInteractionOnUnderlyingViewsEnabled = false
        HUD.show(.progress, onView: UIApplication.shared.windows.last)
    }
    
    private func hideLoading() {
        HUD.hide(afterDelay: 0)
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
    
    @objc private func refreshUsers(_ sender: Any) {
        store.dispatch(action: UsersRequests.FetchUserData(fetchUserDataType: FetchUserDataType.refresh, isInitiatedByUser: true))
        analyticsControler.logEvent(eventName: AnalyticsEvents().USERS_REFRESH, params: [:])
    }
}

fileprivate extension Array where Element == User {
    func mapToItems() -> [UserItem] {
        compactMap { user in
            UserItem.userItem(UserItem.UserItem(user))
        }
    }
}
