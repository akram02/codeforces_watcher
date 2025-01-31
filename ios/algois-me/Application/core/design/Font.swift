import Foundation
import UIKit

public class Font {
    
    public static let textHeading =
        UIFont(name: "Roboto-Regular", size: 18) ?? UIFont.systemFont(ofSize: 18)
    public static let textSubheading =
        UIFont(name: "Roboto-Regular", size: 11) ?? UIFont.systemFont(ofSize: 11)
    public static let textSubheadingBig =
        UIFont(name: "Roboto-Regular", size: 12) ?? UIFont.systemFont(ofSize: 12)
    public static let textBody =
        UIFont(name: "Roboto-Regular", size: 14) ?? UIFont.systemFont(ofSize: 14)
    public static let textPageTitle =
        UIFont(name: "Roboto-Bold", size: 20) ?? UIFont.boldSystemFont(ofSize: 20)
    public static let textButton =
        UIFont(name: "Roboto-Medium", size: 14) ?? UIFont.systemFont(ofSize: 14)
    public static let textHint =
        UIFont(name: "Roboto-Regular", size: 11) ?? UIFont.systemFont(ofSize: 11)
    public static let textHintBold =
        UIFont(name: "Roboto-Medium", size: 11) ?? UIFont.systemFont(ofSize: 11)
    
    public static let monospacedHeaderMedium =
        UIFont.monospacedSystemFont(ofSize: 18, weight: .medium)
    public static let monospacedBodySemibold =
        UIFont.monospacedSystemFont(ofSize: 16, weight: .semibold)
    public static let monospacedBodyRegular =
        UIFont.monospacedSystemFont(ofSize: 16, weight: .regular)
    public static let monospacedBodyRegular2 =
        UIFont.monospacedSystemFont(ofSize: 14, weight: .regular)
    public static let monospacedHintRegular =
        UIFont.monospacedSystemFont(ofSize: 13, weight: .regular)
}
