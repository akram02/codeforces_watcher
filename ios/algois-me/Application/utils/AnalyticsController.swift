import Foundation
import common
import FirebaseAnalytics
import FirebaseCrashlytics

class AnalyticsController: IAnalyticsController {
    
    func logError(message: String) {
        Crashlytics.crashlytics().record(error: CrashlyticsError(message: message))
    }
    
    func logEvent(eventName: String, params: [String: String] = [:]) {
        Analytics.logEvent(eventName, parameters: params)
    }
}

