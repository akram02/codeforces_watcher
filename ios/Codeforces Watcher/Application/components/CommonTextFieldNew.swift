import SwiftUI

struct CommonTextFieldNew: UIViewRepresentable {
    
    @Binding var text: String
    private let placeholder: String
    private let isSecureTextField: Bool
    private let tag: Int
    
    @State private var mask = ""
    @State private var isFocused = false
    
    init(
        text: Binding<String>,
        placeholder: String,
        contentType: CommonTextFieldNew.Kind,
        tag: Int
    ) {
        _text = text
        self.placeholder = placeholder
        self.isSecureTextField = (contentType == .password)
        self.tag = tag
    }

    func makeUIView(context: UIViewRepresentableContext<CommonTextFieldNew>) -> UITextField {
        let textField = UITextField().apply {
            $0.delegate = context.coordinator
            $0.placeholder = placeholder
            $0.tag = tag
            
            $0.font = UIFont.monospacedSystemFont(ofSize: 16, weight: .regular)
            $0.borderStyle = .none
            $0.autocorrectionType = .no
            $0.spellCheckingType = .no
            $0.autocapitalizationType = .none
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.layer.run {
                $0.backgroundColor = Palette.white.cgColor
                $0.masksToBounds = false
                $0.shadowColor = Palette.black.cgColor
                $0.shadowOffset = CGSize(width: 0.0, height: 1.0)
                $0.shadowOpacity = 1.0
                $0.shadowRadius = 0.0
            }

            $0.frame.size.height = 20
        }
        
        return textField
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
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

        func textFieldDidChangeSelection(_ textField: UITextField) {
            DispatchQueue.main.async {
                if self.parent.isSecureTextField {
                    self.handleSecureField(textField)
                } else {
                    self.handleDefaultField(textField)
                }
            }
        }
        
        func handleSecureField(_ textField: UITextField) {
            let text = textField.text ?? ""
            let lastCharacter = text.count > 0 ? String(text.last!) : ""
            
            if lastCharacter == "*" || lastCharacter == "" {
                self.parent.text.removeLast()
            } else {
                self.parent.text += lastCharacter
            }
            
            self.parent.mask = String(repeatElement("*", count: text.count))
        }
        
        func handleDefaultField(_ textField: UITextField) {
            let text = textField.text ?? ""
            
            self.parent.text = text
            self.parent.mask = text
        }

        func textFieldDidBeginEditing(_ textField: UITextField) {
            DispatchQueue.main.async {
                self.parent.isFocused = true
            }
        }

        func textFieldDidEndEditing(_ textField: UITextField) {
            DispatchQueue.main.async {
                self.parent.isFocused = false
            }
        }

        func textFieldShouldReturn(_ textField: UITextField) -> Bool {
            if let nextField = textField.superview?.superview?.viewWithTag(textField.tag + 1) as? UITextField {
                nextField.becomeFirstResponder()
            } else {
                textField.resignFirstResponder()
            }

            return false
        }
    }
    
    enum Kind {
        case email
        case password
        case text
    }
}
