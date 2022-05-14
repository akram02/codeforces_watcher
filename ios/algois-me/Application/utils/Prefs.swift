import Foundation
import common

fileprivate let KEY_PROBLEMS_SELECTED_TAGS = "key_problems_selected_tags"
fileprivate let KEY_PROBLEMS_TAGS = "key_problems_tags"

class Prefs: Settings {

    func readUserAccount() -> UserAccount? {
        guard let json = UserDefaults.standard.value(forKey: "userAccount") as? String else { return nil }
        
        return UserAccount.Companion().fromJson(json: json)
    }
    
    func writeUserAccount(userAccount: UserAccount?) {
        UserDefaults.standard.setValue(userAccount?.toJson(), forKey: "userAccount")
    }
  
    func readContestsFilters() -> Set<String> {
        if let savedFilters = (UserDefaults.standard.value(forKey: "contestsFilters")) as? Array<String> {
            return Set(savedFilters)
        } else {
            return Contest.PlatformCompanion().defaultFilterValueToSave
        }
    }
    
    func writeContestsFilters(filters: Set<String>) {
        UserDefaults.standard.setValue(Array(filters), forKey: "contestsFilters")
    }

    func readProblemsIsFavourite() -> Bool {
        if let savedIsFavourite = (UserDefaults.standard.value(forKey: "isFavouriteProblems")) as? Bool {
            return savedIsFavourite
        } else {
            return false
        }
    }
    
    func writeProblemsIsFavourite(isFavourite: Bool) {
        UserDefaults.standard.setValue(isFavourite, forKey: "isFavouriteProblems")
    }

    func readSpinnerSortPosition() -> Int32 {
        if let spinnerSortPosition = (UserDefaults.standard.value(forKey: "spinnerSortPosition")) as? Int32 {
            return spinnerSortPosition
        } else {
            return 0
        }
    }

    func writeSpinnerSortPosition(spinnerSortPosition: Int32) {
        UserDefaults.standard.setValue(spinnerSortPosition, forKey: "spinnerSortPosition")
    }

    func readLastPinnedPostLink() -> String {
        if let pinnedPostLink = (UserDefaults.standard.value(forKey: "pinnedPostLink")) as? String {
            return pinnedPostLink
        } else {
            return ""
        }
    }

    func writeLastPinnedPostLink(pinnedPostLink: String) {
        UserDefaults.standard.setValue(pinnedPostLink, forKey: "pinnedPostLink")
    }
    
    func resetAllDefaults() {
        let domain = Bundle.main.bundleIdentifier!
        UserDefaults.standard.removePersistentDomain(forName: domain)
        UserDefaults.standard.synchronize()
    }
    
    func readProblemsTags() -> [String] {
        UserDefaults.standard.value(forKey: KEY_PROBLEMS_TAGS) as? Array<String> ?? Array()
    }
    
    func writeProblemsTags(tags: [String]) {
        UserDefaults.standard.setValue(tags, forKey: KEY_PROBLEMS_TAGS)
    }
    
    func readProblemsSelectedTags() -> Set<String> {
        let tags = UserDefaults.standard.value(forKey: KEY_PROBLEMS_SELECTED_TAGS) as? Array<String> ?? Array()
        return Set(tags)
    }
    
    func writeProblemsSelectedTags(tags: Set<String>) {
        UserDefaults.standard.setValue(Array(tags), forKey: KEY_PROBLEMS_SELECTED_TAGS)
    }
}
