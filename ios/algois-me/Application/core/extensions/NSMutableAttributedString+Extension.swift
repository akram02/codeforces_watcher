import Foundation
import UIKit

extension NSMutableAttributedString {
    
    func colored(with color: UIColor, range: NSRange? = nil) -> () {
        addAttribute(NSAttributedString.Key.foregroundColor, value: color, range: range ?? NSRange(location: 0, length: self.length))
    }
    
    func fonted(with font: UIFont, range: NSRange? = nil) {
        addAttribute(NSAttributedString.Key.font, value: font, range: range ?? NSRange(location: 0, length: self.length))
    }
    
    func addLink(url: String, range: NSRange) {
        addAttribute(.link, value: url, range: range)
    }
    
    func addUnderline(range: NSRange) {
        addAttribute(.underlineStyle, value: 1, range: range)
    }
    
    func addKerning(value: CGFloat) {
        addAttribute(NSAttributedString.Key.kern, value: value, range: NSRange(location: 0, length: self.length))
    }
    
    func getRangeAndRemoveTag(tag: String) -> NSRange {
        let openTag = "<\(tag)>"
        let closeTag = "</\(tag)>"
        
        guard let rangeOpenTag = string.range(of: openTag) else { fatalError() }
        guard let rangeCloseTag = string.range(of: closeTag) else { fatalError() }
        
        let indexFirstEntry = rangeOpenTag.upperBound.utf16Offset(in: string) - openTag.count
        let indexLastEntry = rangeCloseTag.lowerBound.utf16Offset(in: string) - 1 - openTag.count
        
        deleteCharacters(in: NSRange(rangeCloseTag, in: string))
        deleteCharacters(in: NSRange(rangeOpenTag, in: string))
        
        return NSRange(location: indexFirstEntry, length: indexLastEntry - indexFirstEntry + 1)
    }
}

func + (left: NSMutableAttributedString, right: NSMutableAttributedString) -> NSMutableAttributedString {
    return NSMutableAttributedString().apply {
        $0.append(left)
        $0.append(right)
    }
}
