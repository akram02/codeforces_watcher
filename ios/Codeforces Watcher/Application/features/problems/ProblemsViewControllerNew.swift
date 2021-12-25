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
    
    private let fabButton = FabButtonViewController()
    private let refreshControl = UIRefreshControl()
    
    init() {
        super.init(rootView: ProblemsView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        setView()
        setRefreshControl()
        setInteractions()
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
        self.hideNavigationBar()
        
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setButton(name: "infinityIcon", action: { self.onFabButton() })
    }
    
    private func setRefreshControl() {
        rootView.refreshControl = refreshControl
        
        refreshControl.run {
            $0.addTarget(self, action: #selector(refreshProblems(_:)), for: .valueChanged)
            $0.tintColor = Palette.colorPrimaryDark
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        if (state.status == .idle) {
            refreshControl.endRefreshing()
        }
        
        updateFabButton(state.isFavourite)
        
        rootView.problems = state.filteredProblems
        rootView.noProblemsExplanation = state.isFavourite ? "no_favourite_problems_explanation" : "problems_explanation"
    }
    
    private func updateFabButton(_ isFavourite: Bool) {
        fabButton.updateImage(name: isFavourite ? "starProblemsIcon" : "infinityIcon")
    }
    
    private func onFabButton() {
        store.dispatch(action: ProblemsActions.ChangeTypeProblems(isFavourite: !store.state.problems.isFavourite))
    }
    
    private func setInteractions() {
        rootView.onFilter = {
            self.presentModal(ProblemsFiltersViewController())
        }
        
        rootView.onProblem = { link, title in
            let webViewController = WebViewController(
                link,
                title,
                AnalyticsEvents().PROBLEM_OPENED,
                AnalyticsEvents().PROBLEM_SHARED
            )
            self.presentModal(webViewController)
        }
    }
    
    @objc private func refreshProblems(_ sender: Any) {
        fetchProblems()
        analyticsControler.logEvent(eventName: AnalyticsEvents().PROBLEMS_REFRESH, params: [:])
    }
    
    private func fetchProblems() {
        store.dispatch(action: ProblemsRequests.FetchProblems(isInitiatedByUser: true))
    }
}
