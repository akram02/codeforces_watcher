import SwiftUI

struct AddUserCardView: View {
    
    var onAddUser: (_ handle: String) -> Void = { _ in }
    
    @State var handle = ""
    @State var shouldClear = false
    
    var body: some View {
        VStack(spacing: 20) {
            TextInputLayoutView(
                text: $handle,
                hint: "codeforces_handle".localized,
                placeholder: "codeforces_handle".localized,
                contentType: .text,
                tag: 0,
                shouldClear: $shouldClear
            )
            
            HStack {
                Spacer()
                
                CommonSmallButton(
                    label: "Add user".localized.uppercased(),
                    action: {
                        onAddUser(handle)
                        shouldClear = true
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

struct AddUserCardView_Previews: PreviewProvider {
    static var previews: some View {
        AddUserCardView()
    }
}
