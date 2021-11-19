//
//  UsersTableViewAdapter.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 4/14/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import Foundation
import UIKit
import common

class UsersTableViewAdapter: NSObject, UITableViewDelegate, UITableViewDataSource {

    var users: [UserItem] = []
    
    var onUserTap: ((String) -> ()) = {_ in }
    var onUserAccountTap: ((String) -> ()) = {_ in }
    
    var onVerifyCellTap: () -> () = {}

    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        guard !users.isEmpty else { return 1 }
        return users.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard !users.isEmpty else {
            return tableView.dequeueReusableCell(cellType: NoItemsTableViewCell.self).apply {
                $0.bind(imageName: "alienImage", explanation: "no_users_explanation")
            }
        }
        
        switch(users[indexPath.row]) {
        case .loginItem(let onLogin):
            return tableView.dequeueReusableCell(cellType: LoginTableViewCellNew.self).apply {
                $0.bind(onLogin: onLogin)
            }
        case .verifyItem(let onVerify):
            return tableView.dequeueReusableCell(cellType: VerifyTableViewCell.self).apply {
                $0.bind(onVerify: onVerify)
            }
        case .userItem(let item):
            return tableView.dequeueReusableCell(cellType: UserTableViewCell.self).apply {
                $0.bind(item)
            }
        case .userAccount(let item):
            return tableView.dequeueReusableCell(cellType: UserAccountTableViewCell.self).apply {
                $0.bind(item, onUserAccountTap: onUserAccountTap)
            }
        case .sectionTitle(let title):
            return tableView.dequeueReusableCell(cellType: TitleSectionTableViewCell.self).apply {
                $0.bind(title)
            }
        }
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        guard !users.isEmpty else { return }
        switch(users[indexPath.row]) {
        case .loginItem, .sectionTitle, .userAccount, .verifyItem:
            break
        case .userItem(let item):
            onUserTap(item.handle)
        }
    }

    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if users.isEmpty {
            return tableView.frame.height
        } else {
            return UITableView.automaticDimension
        }
    }
}
