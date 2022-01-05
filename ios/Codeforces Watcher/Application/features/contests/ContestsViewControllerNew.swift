import SwiftUI
import EventKit
import common

class ContestsViewControllerNew: UIHostingController<ContestsView>, ReKampStoreSubscriber {
    
    private lazy var fabButton = FabButtonViewController(name: "eyeIcon").apply {
        $0.setButtonAction(action: { self.onFabButton() } )
    }
    private let refreshControl = UIRefreshControl()
    
    init() {
        super.init(rootView: ContestsView())
        
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
                KotlinBoolean(bool: oldState.contests == newState.contests)
            }.select { state in
                state.contests
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
    
    private func onFabButton() {
        let contestsLink = "https://clist.by/"
        
        let webViewController = WebViewController(contestsLink, "upcoming_contests".localized)
        presentModal(webViewController)
    }
    
    private func setRefreshControl() {
        rootView.refreshControl = refreshControl
        
        refreshControl.run {
            $0.addTarget(self, action: #selector(refreshContests(_:)), for: .valueChanged)
            $0.tintColor = Palette.black
        }
    }
    
    private func setInteractions() {
        rootView.onContest = { contest in
            let webViewController = WebViewController(
                contest.link,
                contest.title,
                AnalyticsEvents().CONTEST_OPENED,
                AnalyticsEvents().CONTEST_SHARED
            )

            self.presentModal(webViewController)
        }
        
        rootView.onCalendar = { contest in
            self.addEventToCalendar(contest) { success, _ in
                if success {
                    analyticsControler.logEvent(
                        eventName: AnalyticsEvents().ADD_CONTEST_TO_CALENDAR,
                        params: ["contest_name": contest.title, "contest_platform": contest.platform.name]
                    )
                    self.showAlertWithOK(title: contest.title, message: "Has been added to your calendar".localized)
                } else {
                    self.showAlertWithOK(title: "Can't add contest to Calendar without permission".localized, message: "Enable it in Settings, please".localized)
                }
            }
        }
    }
    
    func onNewState(state: Any) {
        let state = state as! ContestsState
        
        if state.status == .idle {
            refreshControl.endRefreshing()
        }

        rootView.contests = state.contests
            .filter { $0.phase == .pending && state.filters.contains($0.platform) }
            .sorted(by: {
                $0.startDateInMillis < $1.startDateInMillis
            })
    }
    
    private func addEventToCalendar(
        _ contest: Contest,
        completion: @escaping (Bool, NSError?) -> Void = { _, _ in }
    ) {
        let eventStore = EKEventStore()

        if EKEventStore.authorizationStatus(for: .event) != EKAuthorizationStatus.authorized {
            eventStore.requestAccess(to: .event, completion: { granted, error in
                DispatchQueue.main.async {
                    if granted && error == nil {
                        self.saveContestEvent(eventStore: eventStore, contest: contest, completion: { success, NSError in
                            completion(success, NSError)
                        })
                    } else {
                        completion(false, error as NSError?)
                    }
                }
            })
        } else {
            saveContestEvent(eventStore: eventStore, contest: contest, completion: { success, NSError in
                completion(success, NSError)
            })
        }
    }
    
    private func saveContestEvent(
        eventStore: EKEventStore,
        contest: Contest,
        completion: @escaping (Bool, NSError?) -> Void = { _, _ in }
    ) {
        let startDate = Date(timeIntervalSince1970: Double(contest.startDateInMillis / 1000))
        let endDate = Date(timeIntervalSince1970: Double(contest.startDateInMillis / 1000 + contest.durationInMillis / 1000))
        
        let event = EKEvent(eventStore: eventStore).apply {
            $0.title = contest.title
            $0.startDate = startDate
            $0.endDate = endDate
            $0.calendar = eventStore.defaultCalendarForNewEvents
        }

        do {
            try eventStore.save(event, span: .thisEvent)
        } catch let e as NSError {
            completion(false, e)
            return
        }
        completion(true, nil)
    }
    
    private func showAlertWithOK(title: String, message: String) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let okButton = UIAlertAction(title: "OK".localized, style: .cancel)
        alertController.addAction(okButton)

        present(alertController, animated: true, completion: nil)
    }
    
    @objc private func refreshContests(_ sender: Any) {
        analyticsControler.logEvent(eventName: AnalyticsEvents().CONTESTS_REFRESH, params: [:])
        fetchContests()
    }
    
    private func fetchContests() {
        store.dispatch(action: ContestsRequests.FetchContests(isInitiatedByUser: true))
    }
}
