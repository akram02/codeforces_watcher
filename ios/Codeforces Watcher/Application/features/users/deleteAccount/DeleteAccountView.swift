import SwiftUI

struct DeleteAccountView: View {
    
    var onDismiss: () -> Void = {}
    var onDeleteAccount: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            NavigationBar(
                title: "delete_account".localized,
                onLeftButton: onDismiss
            )
            
            DeleteAccountExplanation()
            
            Spacer()
            
            BottomBar()
        }
        .padding([.horizontal, .bottom], 20)
    }
    
    private func DeleteAccountExplanation() -> some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("delete_account_warning".localized)
                .font(.bodyRegular)
                .foregroundColor(Palette.red.swiftUIColor)
            
            Text("delete_account_explanation".localized)
                .font(.hintRegular)
                .foregroundColor(Palette.black.swiftUIColor)
            
            Text("delete_account_hint".localized)
                .font(.hintRegular)
                .foregroundColor(Palette.red.swiftUIColor)
        }
    }
    
    private func BottomBar() -> some View {
        VStack(alignment: .center, spacing: 10) {
            CommonBigButton(
                label: "do_not_delete".localized,
                action: onDismiss,
                isInverted: false
            )
            
            CommonBigButton(
                label: "delete_account".localized,
                action: onDeleteAccount,
                isInverted: true
            )
        }
        .frame(maxWidth: .infinity)
    }
}
