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
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        fabButton.show()

        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                return KotlinBoolean(bool: oldState.problems == newState.problems)
            }.select { state in
                return state.problems
            }
        }
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        fabButton.hide()
        
        store.unsubscribe(subscriber: self)
    }
    
    private func setView() {
        self.navigationController?.navigationBar.isHidden = true
        
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setButton(name: "infinityIcon", action: { self.onFabButton() })
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        updateFabButton(state.isFavourite)
        
        rootView.problems = state.filteredProblems
    }
    
    private func updateFabButton(_ isFavourite: Bool) {
        fabButton.updateImage(name: isFavourite ? "starProblemsIcon" : "infinityIcon")
    }
    
    private func onFabButton() {
        store.dispatch(action: ProblemsActions.ChangeTypeProblems(isFavourite: !store.state.problems.isFavourite))
    }
}
