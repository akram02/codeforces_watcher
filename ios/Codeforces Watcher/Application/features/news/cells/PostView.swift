import SwiftUI

struct PostView: View {
    var body: some View {
        VStack(spacing: 0) {
//            PostTextView()
            
            CommentView
        }
        .background(Palette.lightGray.swiftUIColor)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var CommentView: some View {
        HStack {
            CommonText("Be the first to comment")
                .font(.hintRegular)
                .foregroundColor(Palette.darkGray.swiftUIColor)
            
            Spacer()
            
            Image("ic_arrow")
                .resizable()
                .frame(width: 12, height: 12)
        }
        .frame(height: 32)
        .padding(.horizontal, 12)
        .background(Color.clear)
        .cornerRadius(20)
    }
}

struct PostView_Previews: PreviewProvider {
    static var previews: some View {
        PostView()
    }
}
