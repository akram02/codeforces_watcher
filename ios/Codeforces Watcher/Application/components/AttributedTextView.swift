import SwiftUI
import common

struct AttributedTextView: UIViewRepresentable {
    
    let attributedString: NSMutableAttributedString
    let attributeTags: [AttributeTag]
    let font: UIFont
    let foregroundColor: UIColor
    
    @Binding var height: CGFloat
    
    var onLink: (String) -> Void = { _ in }
    
    init(
        attributedString: NSMutableAttributedString,
        attributeTags: [AttributeTag],
        font: UIFont,
        foregroundColor: UIColor,
        height: Binding<CGFloat>,
        onLink: @escaping (String) -> Void = { _ in }
    ) {
        self.attributedString = attributedString
        self.attributeTags = attributeTags
        self.font = font
        self.foregroundColor = foregroundColor
        
        self._height = height
        
        self.onLink = onLink
        
        addAttributes()
    }
    
    func makeUIView(context: UIViewRepresentableContext<AttributedTextView>) -> UITextView {
        let textView = UITextView().apply {
            $0.delegate = context.coordinator
            $0.isEditable = false
            $0.isScrollEnabled = false
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.attributedText = attributedString
            $0.linkTextAttributes = [
                .foregroundColor: Palette.black,
                .underlineStyle: 1
            ]
            $0.textAlignment = .left
            $0.textContainer.lineBreakMode = .byWordWrapping
        }
        
        return textView
    }
    
    func updateUIView(_ uiView: UITextView, context: Context) {
        DispatchQueue.main.async {
            let newSize = uiView.sizeThatFits(
                CGSize(
                    width: uiView.superview?.frame.width ?? 0,
                    height: .greatestFiniteMagnitude
                )
            )
            height = newSize.height
        }
    }
    
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
        attributedString.run {
            $0.fonted(with: font)
            $0.colored(with: foregroundColor)
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
        
        let range = attributedString.getRangeAndRemoveTag(tag: tag)
        
        attributedString.run {
            $0.addLink(url: link, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: font.pointSize, weight: .semibold), range: range)
        }
    }
    
    private func addPrivacyAttribute() {
        let tag = "privacy"
        let link = Constants().PRIVACY_POLICY_LINK
        
        let range = attributedString.getRangeAndRemoveTag(tag: tag)
        
        attributedString.run {
            $0.addLink(url: link, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: font.pointSize, weight: .semibold), range: range)
        }
    }
    
    private func addBoldAttribute() {
        let tag = "bold"
        let range = attributedString.getRangeAndRemoveTag(tag: tag)

        attributedString.run {
            $0.colored(with: Palette.black, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: font.pointSize, weight: .semibold), range: range)
        }
    }
    
    enum AttributeTag {
        case term
        case privacy
        case bold
    }
}
