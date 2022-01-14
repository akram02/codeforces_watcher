import SwiftUI

struct PostTextView: View {
    
    var body: some View {
        VStack(spacing: 12) {
            PostTitleView()
            
            CommonText("A left-leaning red–black (LLRB) tree is a type of self-balancing binary search tree. It is a variant of the red–black tree and guarantees the same guarantees the same...")
                .font(.hintRegular)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(4)
        }
        .frame(maxWidth: .infinity)
        .padding(12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}

struct PostTextView_Previews: PreviewProvider {
    static var previews: some View {
        PostTextView()
    }
}
