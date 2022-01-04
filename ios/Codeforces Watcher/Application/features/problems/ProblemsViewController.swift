import SwiftUI
import common
import FirebaseAnalytics

class ProblemsViewController: UIHostingController<ProblemsView>, ReKampStoreSubscriber {
    
    private lazy var fabButton = FabButtonViewController(
        name: "infinityIcon",
        action: { self.onFabButton() }
    )
    private let refreshControl = UIRefreshControl()
    
    init() {
        super.init(rootView: ProblemsView())
        
        setRefreshControl()
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
    
    private func setFabButton() {
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setButtonAction(action: { self.onFabButton() })
    }
    
    private func setRefreshControl() {
        rootView.refreshControl = refreshControl
        
        refreshControl.run {
            $0.addTarget(self, action: #selector(refreshProblems(_:)), for: .valueChanged)
            $0.tintColor = Palette.black
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        if state.status == .idle {
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
            self.presentModal(ProblemsFiltersViewControllerNew())
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
