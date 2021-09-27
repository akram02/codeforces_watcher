import SwiftUI

struct TextInputLayoutView: View {
    @Binding var textReal: String
    @Binding var textView: String
    var hint: String
    var placeholder: String
    var contentType: TypeOfField
    var tag: Int
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(hint)
                .foregroundColor(.gray)
                .font(.system(size: 13, design: .monospaced))
            
            CommonTextFieldNew(
                textReal: $textReal,
                textView: $textView,
                placeholder: placeholder,
                contentType: contentType,
                tag: tag
            )
                .fixedSize(horizontal: false, vertical: true)
        }
    }
}

struct TextInputLayoutView_Previews: PreviewProvider {
    static var previews: some View {
        TextInputLayoutView(
            textReal: .constant(""),
            textView: .constant(""),
            hint: "Email",
            placeholder: "Email",
            contentType: .email,
            tag: 0
        )
    }
}
