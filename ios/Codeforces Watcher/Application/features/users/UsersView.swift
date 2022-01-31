import SwiftUI

struct UsersView: View {
    
    var users: [UserItem] = []
    
    var onUserAccount: (_ handle: String) -> Void = { _ in }
    var onUser: (_ handle: String) -> Void = { _ in }
    
    var pickerOptions: [String] = []
    var pickerSelectedPosition: Int = 0
    var onOptionSelected: (_ option: Int32) -> Void = { _ in }
    
    var isAddUserCardDisplayed = false
    var addUserCardToggle: () -> Void = {}
    var onAddUser: (_ handle: String) -> Void = { _ in }
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack(alignment: .bottom) {
            VStack(spacing: 0) {
                UsersNavigationBar(
                    title: "Users".localized,
                    pickerOptions: pickerOptions,
                    pickerSelectedPosition: pickerSelectedPosition,
                    onOptionSelected: onOptionSelected
                )
                
                RefreshableScrollView(content: {
                    UsersList
                }, refreshControl: refreshControl)
                    .background(Palette.white.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
            }
            .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
            .disabled(isAddUserCardDisplayed)
            
            if isAddUserCardDisplayed {
                Palette.black.swiftUIColor.opacity(0.5)
                    .edgesIgnoringSafeArea(.all)
                    .onTapGesture {
                        addUserCardToggle()
                    }
                
                AddUserCardView(onAddUser: onAddUser)
            }
        }
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
