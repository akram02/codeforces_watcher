import SwiftUI
import EventKit
import common

class ContestsViewController: UIHostingController<ContestsView>, ReKampStoreSubscriber {
    
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
        
        store.unsubscribe(subscriber: self)
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
        updateFilters(state)
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
    
    private func addEventToCalendar(
        _ contest: ContestView.UIModel,
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
        contest: ContestView.UIModel,
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
    
    private func updateFilters(_ state: ContestsState) {
        let filters = state.filters

        rootView.filterItems = [
            filterItem(title: "Codeforces", platform: .codeforces),
            filterItem(title: "Codeforces Gym", platform: .codeforcesGym),
            filterItem(title: "AtCoder", platform: .atcoder),
            filterItem(title: "LeetCode", platform: .leetcode),
            filterItem(title: "TopCoder", platform: .topcoder),
            filterItem(title: "CS Academy", platform: .csAcademy),
            filterItem(title: "CodeChef", platform: .codechef),
            filterItem(title: "HackerRank", platform: .hackerrank),
            filterItem(title: "HackerEarth", platform: .hackerearth),
            filterItem(title: "Kick Start", platform: .kickStart),
            filterItem(title: "Toph", platform: .toph)
        ]
        
        func filterItem(
            title: String,
            platform: Contest.Platform
        ) -> ContestFilterView.UIModel {
            .init(
                title: title,
                image: Image(Contest.Platform.getImageNameByPlatform(platform)),
                isSelected: filters.contains(platform),
                onFilter: { isSelected in self.onFilter(platform, isSelected) }
            )
        }
    }
    
    private func onFilter(_ platform: Contest.Platform, _ isOn: Bool) {
        store.dispatch(action: ContestsRequests.ChangeFilterCheckStatus(platform: platform, isChecked: isOn))
    }
}
