import SwiftUI

struct UsersView: View {
    
    var users: [UserItem] = []
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            RefreshableScrollView(content: {
                UsersList
            }, refreshControl: refreshControl)
                .background(Palette.white.swiftUIColor)
                .cornerRadius(30, corners: [.topLeft, .topRight])
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    private var UsersList: some View {
        ScrollView {
            LazyVStack {
                ForEach(users.indices, id: \.self) { index in
                    switch users[index] {
                    case .loginItem(let onLogin):
                        CommonText("Login Item")
                    case .verifyItem(let onVerify):
                        CommonText("Verify Item")
                    case .userAccount(let item):
                        CommonText("UserAccount Item")
                    case .userItem(let item):
                        CommonText("User Item")
                    case .sectionTitle(let title):
                        CommonText("Section Title")
                    }
                }
            }
        }
    }
}

struct UsersView_Previews: PreviewProvider {
    static var previews: some View {
        UsersView()
    }
}
