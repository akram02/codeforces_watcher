import SwiftUI
import common

struct AttributedTextView: UIViewRepresentable {
    
    let attributedString: NSMutableAttributedString
    let attributeTags: [AttributeTag]
    let font: UIFont
    let foregroundColor: UIColor
    let alignment: NSTextAlignment
    
    @Binding var height: CGFloat
    
    var onLink: (String) -> Void = { _ in }
    
    init(
        attributedString: NSMutableAttributedString,
        attributeTags: [AttributeTag] = [],
        font: UIFont,
        foregroundColor: UIColor = UIColor(),
        alignment: NSTextAlignment,
        height: Binding<CGFloat> = .constant(0),
        onLink: @escaping (String) -> Void = { _ in }
    ) {
        self.attributedString = attributedString
        self.attributeTags = attributeTags
        self.font = font
        self.foregroundColor = foregroundColor
        self.alignment = alignment

        self.onLink = onLink

        self._height = height
        

        if attributeTags.isEmpty {
            addFont()
        } else {
            addAttributes()
        }
    }
    
    func makeUIView(context: UIViewRepresentableContext<AttributedTextView>) -> UITextView {
        let textView = UITextView().apply {
            $0.delegate = context.coordinator
            $0.isEditable = false
            $0.isScrollEnabled = false
            $0.isUserInteractionEnabled = true
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            $0.backgroundColor = .clear
            
            $0.attributedText = attributedString
            $0.linkTextAttributes = [
                .foregroundColor: Palette.black,
                .underlineStyle: 1
            ]
            
            $0.textAlignment = alignment
            $0.textContainer.lineBreakMode = .byWordWrapping
            $0.textContainer.lineFragmentPadding = 0
            $0.textContainerInset = .zero
        }
        
        return textView
    }
    
    func updateUIView(_ uiView: UITextView, context: Context) {
        uiView.attributedText = attributedString
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
        addFont()
        addForegroundColor()
        
        addTags()
    }
    
    private func addFont() {
        attributedString.run {
            $0.fonted(with: font)
        }
    }
    
    private func addForegroundColor() {
        attributedString.run {
            $0.colored(with: foregroundColor)
        }
    }
    
    private func addTags() {
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
        let tag = "semibold"
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
