import SwiftUI

struct VideoView: View {
    
    var body: some View {
        VStack(spacing: 12) {
            PostTitleView()
            
            Image("video_placeholder")
                .resizable()
                .frame(maxWidth: .infinity)
                .scaledToFit()
                .cornerRadius(10)
        }
        .frame(maxWidth: .infinity)
        .padding(12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
}

struct VideoView_Previews: PreviewProvider {
    static var previews: some View {
        VideoView()
    }
}
