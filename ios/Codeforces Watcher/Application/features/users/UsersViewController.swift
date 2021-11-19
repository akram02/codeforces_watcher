//
//  UsersViewController.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 4/10/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import UIKit
import SwiftUI
import common
import FirebaseAnalytics
import PKHUD

class UsersViewController: UIViewControllerWithFab, ReKampStoreSubscriber {
    
    private let tableView = UITableView()
    private let tableAdapter = UsersTableViewAdapter()
    private let refreshControl = UIRefreshControl()
    
    private let sortTextField = UITextField().apply {
        $0.font = Font.textHeading
        $0.textColor = Palette.black
        $0.tintColor = .clear
        $0.textAlignment = .right
    }
    private let pickerView = UIPickerView()
    private let pickerAdapter = UsersPickerViewAdapter()
    
    private let blackView = UIView().apply {
        $0.isHidden = true
        $0.alpha = 0.5
        $0.backgroundColor = Palette.black
    }
    
    private let bottomInputCardView = AddUserCardView(
        frame: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width, height: 136)
    ).apply {
        $0.isHidden = true
    }
    
    private var followedUsers: [User] = []
    
    override var inputAccessoryView: UIView? {
        get {
            return bottomInputCardView
        }
    }
    
    override var canBecomeFirstResponder: Bool {
        return true
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        resignFirstResponder()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                return KotlinBoolean(bool: oldState.users == newState.users && oldState.auth == newState.auth)
            }.select { state in
                return state
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        store.unsubscribe(subscriber: self)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        bottomInputCardView.shouldMoveFurther = {
            self.addUser()
        }
        
        setupView()
        setupTableView()
        setupPickerView()
    }
    
    private func setupView() {
        view.backgroundColor = .white
        
        buildViewTree()
        setConstraints()
        setInteractions()
        setFabImage(named: "plusIcon")
    }
    
    private func setupPickerView() {
        pickerAdapter.run {
            $0.optionSelected = { position in
                let sortType = UsersState.SortTypeCompanion().getSortType(sortType: Int32(position))
                store.dispatch(action: UsersActions.Sort(sortType: sortType))
            }
            $0.options = ["default", "rating_up", "rating_down", "update_up", "update_down"].map { $0.localized }
        }
        pickerView.run {
            $0.backgroundColor = Palette.white
            $0.delegate = pickerAdapter
            $0.selectRow(Int(store.state.users.sortType.position), inComponent: 0, animated: false)
        }
        
        let doneButton = UIBarButtonItem(
            title: "Done".localized, 
            style: .plain,
            target: self,
            action: #selector(doneTapped)
        )
        
        let toolbar = UIToolbar().apply {
            $0.sizeToFit()
            $0.setItems([doneButton], animated: false)
            $0.isUserInteractionEnabled = true
            $0.barTintColor = Palette.white
            $0.tintColor = Palette.colorPrimary
        }

        sortTextField.run {
            $0.inputView = pickerView
            $0.inputAccessoryView = toolbar
        }
    }
    
    @objc func doneTapped() {
        sortTextField.resignFirstResponder()
    }
    
    private func buildViewTree() {
        view.addSubview(tableView)
        navigationController?.view.addSubview(blackView)
        navigationController?.navigationBar.addSubview(sortTextField)
    }
    
    private func setConstraints() {
        tableView.edgesToSuperview()
        blackView.edgesToSuperview()
        
        sortTextField.run {
            $0.topToSuperview()
            $0.bottomToSuperview()
            $0.trailingToSuperview(offset: 8)
        }
    }
    
    private func setInteractions() {
        blackView.addGestureRecognizer(
            UITapGestureRecognizer(target: self, action: #selector(didTapOutside))
        )
    }
    
    @objc func didTapOutside() {
        hideBottomInputView()
        sortTextField.resignFirstResponder()
    }
    
    private func setupTableView() {
        tableView.run {
            $0.delegate = tableAdapter
            $0.dataSource = tableAdapter
            $0.separatorStyle = .none
            $0.refreshControl = refreshControl
        }
        
        tableAdapter.run {
            $0.onUserTap = { handle in
                self.presentModal(UserViewController(handle, isUserAccount: false))
            }
            $0.onUserAccountTap = { handle in
                self.presentModal(UserViewController(handle, isUserAccount: true))
            }
            $0.onVerifyCellTap = {
                self.showLogOutAlert()
            }
        }

        [LoginTableViewCellNew.self, VerifyTableViewCell.self, UserTableViewCell.self, UserAccountTableViewCell.self, NoItemsTableViewCell.self, TitleSectionTableViewCell.self].forEach(tableView.registerForReuse(cellType:))

        refreshControl.run {
            $0.addTarget(self, action: #selector(refreshUsers), for: .valueChanged)
            $0.tintColor = Palette.colorPrimaryDark
        }
        
        tableView.refreshControl = refreshControl
    }
    
    private func showLogOutAlert() {
        let alertController = UIAlertController(
            title: "log_out".localized,
            message: "log_out_ask".localized,
            preferredStyle: .alert
        )
        
        let okButton = UIAlertAction(title: "OK".localized, style: .cancel) { _ in
            store.dispatch(action: AuthRequests.LogOut())
            self.presentingViewController?.dismiss(animated: true)
        }
        let cancelButton = UIAlertAction(title: "Cancel".localized, style: .destructive)
        
        alertController.addAction(okButton)
        alertController.addAction(cancelButton)
        
        present(alertController, animated: true, completion: nil)
    }
    
    @objc private func refreshUsers() {
        store.dispatch(action: UsersRequests.FetchUserData(fetchUserDataType: FetchUserDataType.refresh, isInitiatedByUser: true))

        analyticsControler.logEvent(eventName: AnalyticsEvents().USERS_REFRESH, params: [:])
    }
    
    override func fabButtonTapped() {
        showBottomInputView()
    }
    
    private func addUser() {
        PKHUD.sharedHUD.userInteractionOnUnderlyingViewsEnabled = false
        HUD.show(.progress, onView: UIApplication.shared.windows.last)
        
        let handle = bottomInputCardView.textField.text ?? ""
        store.dispatch(action: UsersRequests.AddUser(handle: handle))
    }
    
    private func showBottomInputView() {
        becomeFirstResponder()
        bottomInputCardView.run {
            $0.isHidden = false
            $0.textField.becomeFirstResponder()
        }
        blackView.isHidden = false
    }
    
    private func hideBottomInputView() {
        bottomInputCardView.textField.run {
            $0.text = ""
            $0.resignFirstResponder()
        }
        resignFirstResponder()
        blackView.isHidden = true
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
    
    func onNewState(state: Any) {
        let state = state as! AppState
        
        let userState = state.users
        let authState = state.auth
        
        if (userState.status == .idle) {
            refreshControl.endRefreshing()
        }
        
        followedUsers = userState.followedUsers
        let sortedUsers = sortUsers(userState.sortType)
        
        switch (authState.authStage) {
        case .notSignedIn:
            let onLogin = {
                self.presentModal(SignInViewController())
                analyticsControler.logEvent(eventName: AnalyticsEvents().SIGN_IN_OPENED, params: [:])
            }
            tableAdapter.users =
                [.loginItem(onLogin)] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        case .signedIn:
            let uiModel = DoActionToIdentifyView.UIModel(
                title: "verify_account".localized,
                subtitle: "verify_account_prompt".localized,
                buttonText: "verify_in_42_seconds".localized,
                onButtonTap: {
                    self.presentModal(VerifyViewController())
                    analyticsControler.logEvent(eventName: AnalyticsEvents().VERIFY_OPENED, params: [:])
                }
            )
            tableAdapter.users =
                [.verifyItem(uiModel)] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        case .verified:
            guard let codeforcesUser = userState.userAccount?.codeforcesUser else { fatalError() }
            tableAdapter.users =
                [.userAccount(UserItem.UserAccountItem(codeforcesUser))] +
                [.sectionTitle("followed_users".localized)] +
                sortedUsers.mapToItems()
        default:
            break
        }
        
        sortTextField.isHidden = followedUsers.isEmpty
        
        tableView.reloadData()
        
        switch (userState.addUserStatus) {
        case .done:
            HUD.hide(afterDelay: 0)
            
            hideBottomInputView()
            
            store.dispatch(action: UsersActions.ClearAddUserState())
        case .idle:
            HUD.hide(afterDelay: 0)
        default:
            break
        }
        
        let currentOption = pickerAdapter.options[Int(userState.sortType.position)]
        sortTextField.text = "Sort".localizedFormat(args: currentOption)
    }
}

fileprivate extension Array where Element == User {
    func mapToItems() -> [UserItem] {
        compactMap { user in
            UserItem.userItem(UserItem.UserItem(user))
        }
    }
}
