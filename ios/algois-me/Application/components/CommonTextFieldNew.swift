import SwiftUI

struct CommonTextFieldNew: UIViewRepresentable {
    
    @Binding var text: String
    private let placeholder: NSMutableAttributedString
    private let isSecureTextField: Bool
    private let tag: Int
    @Binding var shouldClear: Bool
    
    @State private var mask = ""
    
    init(
        text: Binding<String>,
        placeholder: String,
        contentType: CommonTextFieldNew.Kind,
        tag: Int,
        shouldClear: Binding<Bool> = .constant(false)
    ) {
        _text = text
        self.placeholder = placeholder.attributed
        self.isSecureTextField = (contentType == .password)
        self.tag = tag
        _shouldClear = shouldClear
        
        self.placeholder.addAttributes(
            [
                NSAttributedString.Key.foregroundColor : Palette.darkGray,
                NSAttributedString.Key.kern: -1
            ],
            range: NSRange(location: 0, length: self.placeholder.length)
        )
    }

    func makeUIView(context: UIViewRepresentableContext<CommonTextFieldNew>) -> UITextField {
        let textField = UITextField().apply {
            $0.delegate = context.coordinator
            $0.borderStyle = .none
            $0.autocorrectionType = .no
            $0.spellCheckingType = .no
            $0.autocapitalizationType = .none
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.attributedPlaceholder = placeholder
            $0.tag = tag
            
            $0.font = Font.monospacedBodyRegular
            $0.backgroundColor = Palette.white
            $0.defaultTextAttributes.updateValue(-1, forKey: NSAttributedString.Key.kern)

            $0.frame.size.height = 20
        }
        
        return textField
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
        DispatchQueue.main.async {
            if shouldClear {
                text = ""
                mask = ""
                shouldClear = false
            }
        }
        uiView.text = mask
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, UITextFieldDelegate {
        var parent: CommonTextFieldNew
        
        init(_ customTextField: CommonTextFieldNew) {
            parent = customTextField
        }
        
        func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            if let textRange = Range(range, in: self.parent.text) {
                let cursorLocation = textField.position(
                    from: textField.beginningOfDocument,
                    offset: (range.location + string.count)
                )
                
                self.parent.text = self.parent.text.replacingCharacters(in: textRange, with: string)
                self.parent.mask = self.parent.isSecureTextField ? self.secureFieldMask() : self.textFieldMask()
                
                moveCursor(textField, location: cursorLocation)
            }
            
            return false
        }

        func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            if let nextField = textField.superview?.superview?.viewWithTag(textField.tag + 1) as? UITextField {
                nextField.becomeFirstResponder()
            } else {
                textField.resignFirstResponder()
            }

            return false
        }
        
        private func moveCursor(_ textField: UITextField, location: UITextPosition?) {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.001) {
                if let location = location {
                    textField.selectedTextRange = textField.textRange(from: location, to: location)
                }
            }
        }
        
        private func secureFieldMask() -> String {
            String(repeatElement("*", count: self.parent.text.count))
        }

        private func textFieldMask() -> String {
            self.parent.text
        }
    }
    
    enum Kind {
        case email
        case password
        case text
    }
}
