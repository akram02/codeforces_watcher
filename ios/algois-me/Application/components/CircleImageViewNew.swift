import SwiftUI
import SDWebImageSwiftUI

struct CircleImageViewNew: View {
    
    private let noImageAvatarLink = "https://userpic.codeforces.org/no-avatar.jpg"
    
    let userAvatar: String
    let borderColor: Color
    let size: (width: CGFloat, height: CGFloat)
    
    var body: some View {
        if noImageAvatarLink == userAvatar {
            Image("noImage")
                .resizable()
                .frame(width: size.width, height: size.height)
                .clipShape(Circle())
                .overlay(
                    Circle()
                        .strokeBorder(borderColor, lineWidth: 1)
                )
        } else {
            WebImage(url: URL(string: userAvatar))
                .resizable()
                .placeholder(Image("noImage"))
                .frame(width: size.width, height: size.height)
                .clipShape(Circle())
                .overlay(
                    Circle()
                        .strokeBorder(borderColor, lineWidth: 1)
                )
        }
    }
}

struct CircleImageViewNew_Previews: PreviewProvider {
    static var previews: some View {
        CircleImageViewNew(
            userAvatar: "noImage",
            borderColor: Palette.gray.swiftUIColor,
            size: (36, 36)
        )
    }
}
