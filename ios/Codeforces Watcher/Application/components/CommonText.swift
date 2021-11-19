import SwiftUI

struct CommonText: View {
    
    let text: String
    let isUnderlined: Bool
    
    let kerning = CGFloat(-1)
    
    init(
        _ text: String,
        isUnderlined: Bool = false
    ) {
        self.text = text
        self.isUnderlined = isUnderlined
    }
    
    var body: some View {
        Text(text)
            .kerning(kerning)
            .underline(isUnderlined)
    }
}

struct CommonText_Previews: PreviewProvider {
    static var previews: some View {
        CommonText("Text")
    }
}
