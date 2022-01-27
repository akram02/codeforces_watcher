import SwiftUI

struct AddUserCardViewNew: View {
    
    var onAddUser: (_ handle: String) -> Void = { _ in }
    
    @State var handle = ""
    
    var body: some View {
        VStack(spacing: 20) {
            TextInputLayoutView(
                text: $handle,
                hint: "codeforces_handle".localized,
                placeholder: "codeforces_handle".localized,
                contentType: .text,
                tag: 0
            )
            
            HStack {
                Spacer()
                
                CommonSmallButton(
                    label: "Add user".localized.uppercased(),
                    action: {
                        onAddUser(handle)
                    },
                    foregroundColor: Palette.white.swiftUIColor,
                    backgroundColor: Palette.black.swiftUIColor
                )
            }
        }
        .padding(20)
        .background(Palette.white.swiftUIColor)
        .cornerRadius(30, corners: [.topLeft, .topRight])
    }
}

struct AddUserCardViewNew_Previews: PreviewProvider {
    static var previews: some View {
        AddUserCardViewNew()
    }
}
