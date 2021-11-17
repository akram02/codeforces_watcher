import SwiftUI

struct CommonText: View {
    
    let text: String
    let underlined: Bool
    
    let kerning = CGFloat(-1)
    
    init(
        _ text: String,
        underlined: Bool = false
    ) {
        self.text = text
        self.underlined = underlined
    }
    
    var body: some View {
        Text(text)
            .kerning(kerning)
            .underline(underlined)
    }
}

struct CommonText_Previews: PreviewProvider {
    static var previews: some View {
        CommonText("Text")
    }
}
