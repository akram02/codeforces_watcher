import SwiftUI
import common

struct SearchTextField: UIViewRepresentable {
    
    @Binding var text: String
    var placeholder: NSMutableAttributedString
    @Binding var willShow: Bool
    @Binding var willHide: Bool
    
    init(
        text: Binding<String>,
        placeholder: String,
        willShow: Binding<Bool>,
        willHide: Binding<Bool>
    ) {
        _text = text
        self.placeholder = placeholder.attributed
        _willShow = willShow
        _willHide = willHide
        
        self.placeholder.addAttributes(
            [
                NSAttributedString.Key.foregroundColor : Palette.black,
                NSAttributedString.Key.kern: -1
            ],
            range: NSRange(location: 0, length: self.placeholder.length)
        )
    }

    func makeUIView(context: UIViewRepresentableContext<SearchTextField>) -> UITextField {
        let textField = UITextField().apply {
            $0.delegate = context.coordinator
            $0.borderStyle = .none
            $0.autocorrectionType = .no
            $0.spellCheckingType = .no
            $0.autocapitalizationType = .none
            $0.setContentCompressionResistancePriority(.defaultLow, for: .horizontal)
            
            $0.layer.run {
                $0.masksToBounds = true
                $0.cornerRadius = 15
            }
            
            $0.attributedPlaceholder = placeholder
            
            $0.font = Font.monospacedBodyRegular
            $0.backgroundColor = Palette.white
            $0.defaultTextAttributes.updateValue(-1, forKey: NSAttributedString.Key.kern)

            $0.frame.size.height = 30
            $0.leftView = UIView(frame: CGRect(x: 0, y: 0, width: 10, height: $0.frame.size.height))
            $0.leftViewMode = .always
        }
        
        return textField
    }
    
    func updateUIView(_ uiView: UITextField, context: Context) {
        DispatchQueue.main.async {
            if willShow {
                uiView.becomeFirstResponder()
            }
            if willHide {
                text = ""
                uiView.resignFirstResponder()
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.3) {
                    context.coordinator.problemsRequest(query: "")
                }
            }
        }
        uiView.attributedPlaceholder = placeholder
        uiView.text = text
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, UITextFieldDelegate {
        var parent: SearchTextField
        
        init(_ customTextField: SearchTextField) {
            parent = customTextField
        }
        
        func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
            if let textRange = Range(range, in: self.parent.text) {
                let cursorLocation = textField.position(
                    from: textField.beginningOfDocument,
                    offset: (range.location + string.count)
                )
                
                self.parent.text = self.parent.text.replacingCharacters(in: textRange, with: string)
                problemsRequest(query: self.parent.text)
                
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
        
        func problemsRequest(query: String) {
            store.dispatch(action: ProblemsRequests.SetQuery(query: query))
        }
    }
}
