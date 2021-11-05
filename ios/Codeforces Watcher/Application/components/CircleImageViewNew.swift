import SwiftUI
import SDWebImageSwiftUI

struct CircleImageViewNew: View {
    
    var userAvatar: String
    
    let noImageAvatarLink = "https://userpic.codeforces.org/no-avatar.jpg"
    
    var body: some View {
        if noImageAvatarLink == userAvatar {
            Image("noImage")
                .clipShape(Circle())
        } else {
            WebImage(url: URL(string: userAvatar))
                .resizable()
                .placeholder(Image("noImage"))
                .clipShape(Circle())
                .frame(width: 36, height: 36)
        }
    }
}

struct CircleImageViewNew_Previews: PreviewProvider {
    static var previews: some View {
        CircleImageViewNew(userAvatar: "noImage")
    }
}
