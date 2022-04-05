import SwiftUI

struct TextInputLayoutView: View {
    
    @Binding var text: String
    let hint: String
    let placeholder: String
    let contentType: CommonTextFieldNew.Kind
    let tag: Int
    @Binding var shouldClear: Bool
    
    init(
        text: Binding<String>,
        hint: String,
        placeholder: String,
        contentType: CommonTextFieldNew.Kind,
        tag: Int,
        shouldClear: Binding<Bool> = .constant(false)
    ) {
        _text = text
        self.hint = hint
        self.placeholder = placeholder
        self.contentType = contentType
        self.tag = tag
        _shouldClear = shouldClear
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            CommonText(hint)
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
            
            CommonTextFieldNew(
                text: $text,
                placeholder: placeholder,
                contentType: contentType,
                tag: tag,
                shouldClear: $shouldClear
            )
            .fixedSize(horizontal: false, vertical: true)
            
            Divider()
                .frame(height: 1)
                .background(Palette.black.swiftUIColor)
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
