import SwiftUI
import common

struct AttributedTextView: UIViewRepresentable {
    
    let attributedText: NSMutableAttributedString
    let attributeTags: [AttributeTagKind]
    let defaultFont: UIFont
    let defaultTextColor: UIColor
    
    var onLink: (String) -> Void = { _ in }
    
    init(
        attributedText: NSMutableAttributedString,
        attributeTags: [AttributeTagKind],
        defaultFont: UIFont,
        defaultTextColor: UIColor,
        onLink: @escaping (String) -> Void = { _ in }
    ) {
        self.attributedText = attributedText
        self.attributeTags = attributeTags
        self.defaultFont = defaultFont
        self.defaultTextColor = defaultTextColor
        
        self.onLink = onLink
        
        addAttributes()
    }
    
    func makeUIView(context: UIViewRepresentableContext<AttributedTextView>) -> UITextView {
        let textView = UITextView().apply {
            $0.delegate = context.coordinator
            $0.isEditable = false
            $0.isScrollEnabled = false
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.attributedText = attributedText
            $0.linkTextAttributes = [
                .foregroundColor: Palette.black,
                .underlineStyle: 1
            ]
            $0.textAlignment = .left
        }
        
        return textView
    }
    
    func updateUIView(_ uiView: UITextView, context: Context) {}
    
    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }
    
    class Coordinator: NSObject, UITextViewDelegate {
        var parent: AttributedTextView
        
        init(_ attributedTextView: AttributedTextView) {
            self.parent = attributedTextView
        }
        
        func textView(_ textView: UITextView, shouldInteractWith URL: URL, in characterRange: NSRange, interaction: UITextItemInteraction) -> Bool {
            self.parent.onLink(URL.absoluteString)
            return false
        }
        
        func textViewDidChangeSelection(_ textView: UITextView) {
            textView.selectedTextRange = nil
        }
    }
    
    private func addAttributes() {
        attributedText.run {
            $0.fonted(with: defaultFont)
            $0.colored(with: defaultTextColor)
        }
        
        for tagId in 0 ..< attributeTags.count {
            let tag = attributeTags[tagId]
            
            switch tag {
            case .term:
                addTermsAttribute()
            case .privacy:
                addPrivacyAttribute()
            case .bold:
                addBoldAttribute()
            }
        }
    }
    
    private func addTermsAttribute() {
        let tag = "terms"
        let link = Constants().TERMS_AND_CONDITIONS_LINK
        
        let range = attributedText.getRangeAndRemoveTag(tag: tag)
        
        attributedText.run {
            $0.addLink(url: link, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: defaultFont.pointSize, weight: .semibold), range: range)
        }
    }
    
    private func addPrivacyAttribute() {
        let tag = "privacy"
        let link = Constants().PRIVACY_POLICY_LINK
        
        let range = attributedText.getRangeAndRemoveTag(tag: tag)
        
        attributedText.run {
            $0.addLink(url: link, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: defaultFont.pointSize, weight: .semibold), range: range)
        }
    }
    
    private func addBoldAttribute() {
        let tag = "bold"
        let range = attributedText.getRangeAndRemoveTag(tag: tag)

        attributedText.run {
            $0.colored(with: Palette.black, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: defaultFont.pointSize, weight: .semibold), range: range)
        }
    }
    
    enum AttributeTagKind {
        case term
        case privacy
        case bold
    }
}
