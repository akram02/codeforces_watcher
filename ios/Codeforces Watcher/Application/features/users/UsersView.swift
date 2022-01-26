import SwiftUI

struct UsersView: View {
    
    var users: [UserItem] = []
    
    var onUserAccount: (_ handle: String) -> Void = { _ in }
    var onUser: (_ handle: String) -> Void = { _ in }
    
    var pickerOptions: [String] = []
    var onOptionSelected: (_ option: Int32) -> Void = { _ in }
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            UsersNavigationBar(
                title: "Users".localized,
                pickerOptions: pickerOptions,
                onOptionSelected: onOptionSelected
            )
            
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
                        LoginViewCell(onLogin: onLogin)
                    case .verifyItem(let onVerify):
                        VerifyViewCell(onVerify: onVerify)
                    case .userAccount(let item):
                        UserAccountViewCell(user: UserAccountViewCell.UIModel(item), onViewProfile: onUserAccount)
                    case .userItem(let item):
                        UserViewCell(user: item, onUser: onUser)
                    case .sectionTitle(let title):
                        TitleSectionViewCell(title: title)
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
