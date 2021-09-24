import SwiftUI

struct CommonTextFieldNew: UIViewRepresentable {
    @Binding var text: String
    let placeholder: String
    let isSecureTextField: Bool
    let tag: Int
    
    @State var isFocused = false

    func makeUIView(context: UIViewRepresentableContext<CommonTextFieldNew>) -> UITextField {
        let textField = UITextField().apply {
            $0.delegate = context.coordinator
            $0.placeholder = placeholder
            $0.tag = tag
            
            $0.font = Font.textField
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
        uiView.text = text
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
                    self.parent.text = String(repeatElement("*", count: textField.text?.count ?? 0))
                } else {
                    self.parent.text = textField.text ?? ""
                }
            }
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
}
