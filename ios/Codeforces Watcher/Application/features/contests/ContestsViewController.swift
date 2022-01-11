import SwiftUI
import EventKit
import common

class ContestsViewController: UIHostingController<ContestsView>, ReKampStoreSubscriber {
    
    private lazy var fabButton = FabButtonViewController(name: "eyeIcon").apply {
        $0.setButtonAction(action: { self.onFabButton() } )
    }
    
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
        rootView.refreshControl.run {
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
                        params: ["contest_name": contest.title, "contest_platform": contest.platformTitle]
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
            rootView.refreshControl.endRefreshing()
        }
        
        updateContests(state)
        updateContestFilters(state)
    }
    
    private func updateContests(_ state: ContestsState) {
        rootView.contests = state.contests
            .filter { $0.phase == .pending && state.filters.contains($0.platform) }
            .sorted(by: {
                $0.startDateInMillis < $1.startDateInMillis
            })
            .map {
                .init(
                    startDateMonth: Double($0.startDateInMillis / 1000).secondsToContestDateMonthString(),
                    title: $0.title,
                    platformTitle: $0.platform.name,
                    platformLogoTitle: Contest.Platform.getImageNameByPlatform($0.platform),
                    link: $0.link,
                    startDateInMillis: $0.startDateInMillis,
                    durationInMillis: $0.durationInMillis,
                    date: Double($0.startDateInMillis / 1000).secondsToContestDateString()
                )
            }
    }
    
    private func updateContestFilters(_ state: ContestsState) {
        let filters = state.filters

        rootView.filterItems = [
            .init(
                title: "Codeforces",
                image: Image(Contest.Platform.getImageNameByPlatform(.codeforces)),
                isSelected: filters.contains(.codeforces),
                onFilter: { isSelected in self.onFilter(.codeforces, isSelected) }
            ),
            .init(
                title: "Codeforces Gym",
                image: Image(Contest.Platform.getImageNameByPlatform(.codeforcesGym)),
                isSelected: filters.contains(.codeforcesGym),
                onFilter: { isSelected in self.onFilter(.codeforcesGym, isSelected) }
            ),
            .init(
                title: "AtCoder",
                image: Image(Contest.Platform.getImageNameByPlatform(.atcoder)),
                isSelected: filters.contains(.atcoder),
                onFilter: { isSelected in self.onFilter(.atcoder, isSelected) }
            ),
            .init(
                title: "LeetCode",
                image: Image(Contest.Platform.getImageNameByPlatform(.leetcode)),
                isSelected: filters.contains(.leetcode),
                onFilter: { isSelected in self.onFilter(.leetcode, isSelected) }
            ),
            .init(
                title: "TopCoder",
                image: Image(Contest.Platform.getImageNameByPlatform(.topcoder)),
                isSelected: filters.contains(.topcoder),
                onFilter: { isSelected in self.onFilter(.topcoder, isSelected) }
            ),
            .init(
                title: "CS Academy",
                image: Image(Contest.Platform.getImageNameByPlatform(.csAcademy)),
                isSelected: filters.contains(.csAcademy),
                onFilter: { isSelected in self.onFilter(.csAcademy, isSelected) }
            ),
            .init(
                title: "CodeChef",
                image: Image(Contest.Platform.getImageNameByPlatform(.codechef)),
                isSelected: filters.contains(.codechef),
                onFilter: { isSelected in self.onFilter(.codechef, isSelected) }
            ),
            .init(
                title: "HackerRank",
                image: Image(Contest.Platform.getImageNameByPlatform(.hackerrank)),
                isSelected: filters.contains(.hackerrank),
                onFilter: { isSelected in self.onFilter(.hackerrank, isSelected) }
            ),
            .init(
                title: "HackerEarth",
                image: Image(Contest.Platform.getImageNameByPlatform(.hackerearth)),
                isSelected: filters.contains(.hackerearth),
                onFilter: { isSelected in self.onFilter(.hackerearth, isSelected) }
            ),
            .init(
                title: "Kick Start",
                image: Image(Contest.Platform.getImageNameByPlatform(.kickStart)),
                isSelected: filters.contains(.kickStart),
                onFilter: { isSelected in self.onFilter(.kickStart, isSelected) }
            ),
            .init(
                title: "Toph",
                image: Image(Contest.Platform.getImageNameByPlatform(.toph)),
                isSelected: filters.contains(.toph),
                onFilter: { isSelected in self.onFilter(.toph, isSelected) }
            )
        ]
    }
    
    private func addEventToCalendar(
        _ contest: ContestsView.ContestUIModel,
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
        contest: ContestsView.ContestUIModel,
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
    
    private func onFilter(_ platform: Contest.Platform, _ isOn: Bool) {
        store.dispatch(action: ContestsRequests.ChangeFilterCheckStatus(platform: platform, isChecked: isOn))
    }
}
