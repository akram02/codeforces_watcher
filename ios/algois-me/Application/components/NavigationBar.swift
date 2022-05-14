import SwiftUI

struct NavigationBar: View {
    
    var title: String?
    var leftButtonName: String
    var onLeftButton: () -> Void
    var rightButtonName: String?
    var onRightButton: () -> Void
    
    init(
        title: String? = nil,
        leftButtonName: String = "back_arrow",
        onLeftButton: @escaping () -> Void = {},
        rightButtonName: String? = nil,
        onRightButton: @escaping () -> Void = {}
    ) {
        self.title = title
        self.leftButtonName = leftButtonName
        self.onLeftButton = onLeftButton
        self.rightButtonName = rightButtonName
        self.onRightButton = onRightButton
    }
    
    var body: some View {
        HStack(spacing: 20) {
            Button(action: {
                onLeftButton()
            }, label: {
                Image(leftButtonName)
                    .foregroundColor(Palette.black.swiftUIColor)
            })
            
            Text(title ?? "")
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(1)
            
            Spacer()
            
            Button(action: {
                onRightButton()
            }, label: {
                if let rightButtonName = rightButtonName {
                    Image(rightButtonName)
                        .foregroundColor(Palette.black.swiftUIColor)
                } else {
                    EmptyView()
                }
            })
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .frame(height: 56)
    }
}
