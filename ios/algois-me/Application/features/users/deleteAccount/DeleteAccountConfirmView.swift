import SwiftUI

struct DeleteAccountConfirmView: View {
    
    var message: String = ""
    var onDismiss: () -> Void = {}
    var onDeleteAccount: (Bool) -> Void = { _ in }
    
    @State private var isAccepted = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            NavigationBar(
                title: "delete_account".localized,
                onLeftButton: onDismiss
            )
            
            DeleteAccountExplanation()
            
            ErrorMessageView(message: message)
                .frame(maxWidth: .infinity, alignment: .center)
            
            Spacer()
            
            BottomBar()
        }
        .padding([.horizontal, .bottom], 20)
    }
    
    private func DeleteAccountExplanation() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("delete_account_warning".localized)
                .foregroundColor(Palette.red.swiftUIColor)
            
            Text("delete_account_sure".localized)
                .foregroundColor(Palette.black.swiftUIColor)
            
            Text("delete_account_hint".localized)
                .foregroundColor(Palette.red.swiftUIColor)
        }
        .font(.headerMedium)
    }
    
    private func BottomBar() -> some View {
        VStack(alignment: .center, spacing: 20) {
            HStack(spacing: 12) {
                Button(action: {
                    self.isAccepted.toggle()
                }, label: {
                    Image(isAccepted ? "ic_checkbox_checked" : "ic_checkbox_unchecked")
                })
                
                Text("want_to_delete_account".localized)
                    .font(.hintRegular)
                    .foregroundColor(Palette.black.swiftUIColor)
            }
            
            CommonBigButton(
                label: "delete_account".localized,
                action: { onDeleteAccount(isAccepted) },
                isInverted: false
            )
        }
        .frame(maxWidth: .infinity)
    }
}
