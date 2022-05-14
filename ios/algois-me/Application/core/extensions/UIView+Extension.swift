import Foundation
import UIKit

extension UIView {
    
    func onTap(target: Any, action: Selector) {
        isUserInteractionEnabled = true
        addGestureRecognizer(UITapGestureRecognizer(target: target, action: action))
    }
}
