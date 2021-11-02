import SwiftUI

struct TextInputLayoutView: View {
    
    @Binding var text: String
    let hint: String
    let placeholder: String
    let contentType: CommonTextFieldNew.Kind
    let tag: Int
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            Text(hint)
                .foregroundColor(Palette.darkGray.swiftUIColor)
                .font(.hintRegular)
            
            CommonTextFieldNew(
                text: $text,
                placeholder: placeholder,
                contentType: contentType,
                tag: tag
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
