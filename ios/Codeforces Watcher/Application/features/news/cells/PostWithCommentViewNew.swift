import SwiftUI

struct PostWithCommentViewNew: View {
    var body: some View {
        VStack(spacing: 10) {
            PostTextView()
            
            CommentView
            
            AllCommentsView
        }
        .background(Palette.lightGray.swiftUIColor)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var CommentView: some View {
        HStack(alignment: .top, spacing: 6) {
            Image("noImage")
            
            VStack(spacing: 4) {
                HStack(spacing: 0) {
                    CommonText("errichto")
                        .foregroundColor(Palette.red.swiftUIColor)
                    
                    CommonText(" · 5 minutes ago")
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                
                CommonText("It's not that interesting really. It's O(n) running time to delete...")
                    .foregroundColor(Palette.black.swiftUIColor)
                    .lineLimit(2)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .font(.hintRegular)
            .padding(.horizontal, 10)
            .padding(.vertical, 6)
            .background(Palette.accentGrayish.swiftUIColor)
            .cornerRadius(15, corners: [.topRight, .bottomLeft, .bottomRight])
        }
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 12)
        .background(Color.clear)
    }
    
    @ViewBuilder
    private var AllCommentsView: some View {
        HStack {
            CommonText("See all comments")
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
            
            Spacer()
            
            Image("ic_arrow")
                .resizable()
                .frame(width: 12, height: 12)
        }
        .frame(height: 32)
        .padding(.horizontal, 12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}

struct PostWithCommentViewNew_Previews: PreviewProvider {
    static var previews: some View {
        PostWithCommentViewNew()
    }
}
