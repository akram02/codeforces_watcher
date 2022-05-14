import Foundation
import common

extension Contest.Platform {
    
    static func getImageNameByPlatform(_ platform: Contest.Platform) -> String {
        switch (platform) {
        case .codeforces:
            return "Codeforces"
        case .codeforcesGym:
            return "CodeforcesGym"
        case .atcoder:
            return "AtCoder"
        case .codechef:
            return "CodeChef"
        case .csAcademy:
            return "CS Academy"
        case .hackerearth:
            return "HackerEarth"
        case .hackerrank:
            return "HackerRank"
        case .kickStart:
            return "Kick Start"
        case .leetcode:
            return "LeetCode"
        case .topcoder:
            return "TopCoder"
        case .toph:
            return "Toph"
        default:
            return ""
        }
    }
}
