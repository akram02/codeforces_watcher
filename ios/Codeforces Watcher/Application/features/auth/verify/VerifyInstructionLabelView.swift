import SwiftUI

struct VerifyInstructionLabelView: UIViewRepresentable {
    
    let text: NSMutableAttributedString
    
    func makeUIView(context: UIViewRepresentableContext<VerifyInstructionLabelView>) -> UILabel {
        let instructionText = UILabel().apply {
            $0.font = UIFont.monospacedSystemFont(ofSize: 14, weight: .regular)
            $0.textColor = Palette.darkGray
            
            $0.numberOfLines = 0
            $0.textAlignment = .left
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.attributedText = attributedStyleText(tag: "bold")
        }
        
        return instructionText
    }
    
    func updateUIView(_ uiView: UILabel, context: Context) {}
    
    private func attributedStyleText(tag: String) -> NSMutableAttributedString {
        let range = text.getRangeAndRemoveTag(tag: tag)
        
        text.run {
            $0.colored(with: Palette.black, range: range)
            $0.fonted(with: UIFont.monospacedSystemFont(ofSize: 14, weight: .semibold), range: range)
        }
        
        return text
    }
}
