//
//  ProblemsViewController.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 1/17/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import SwiftUI
import common
import FirebaseAnalytics

class ProblemsViewControllerNew: UIHostingController<ProblemsView>, ReKampStoreSubscriber {
    
    let fabButton = FabButtonViewController()
    
    init() {
        super.init(rootView: ProblemsView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                return KotlinBoolean(bool: oldState.problems == newState.problems)
            }.select { state in
                return state.problems
            }
        }
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)

        hideNavigationBar()
        
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setImage(name: "infinityIcon")
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        fabButton.hide()
        
        store.unsubscribe(subscriber: self)
    }

    private func hideNavigationBar() {
        self.navigationController?.setNavigationBarHidden(true, animated: false)
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        rootView.problems = state.filteredProblems
    }
}
