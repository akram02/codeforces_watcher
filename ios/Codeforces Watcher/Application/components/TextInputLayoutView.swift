import SwiftUI

struct TextInputLayoutView: View {
    
    @Binding var text: String
    var hint: String
    var placeholder: String
    var contentType: CommonTextFieldNew.Kind
    var tag: Int
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(hint)
                .font(.textHint)
                .foregroundColor(Palette.gray.swiftUIColor)
            
            CommonTextFieldNew(
                text: $text,
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
            text: .constant(""),
            hint: "Email",
            placeholder: "Email",
            contentType: .email,
            tag: 0
        )
    }
}
