import SwiftUI

struct FeedbackView: View {
    
    var post: NewsItem.FeedbackItem
    var callback: () -> Void = {}
    
    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            HStack(alignment: .top) {
                Title
                
                Spacer()
                
                CloseButton
            }
            
            HStack(spacing: 20) {
                PositiveButton
                
                NegativeButton
            }
        }
        .frame(maxWidth: .infinity)
        .padding(16)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
    }
    
    @ViewBuilder
    private var Title: some View {
        CommonText(post.textTitle)
            .font(SwiftUI.Font.system(size: 22, weight: .regular, design: .monospaced))
            .foregroundColor(Palette.black.swiftUIColor)
    }
    
    @ViewBuilder
    private var CloseButton: some View {
        Button(action: {
            post.neutralButtonClick()
            callback()
        }, label: {
            Image("crossIconNew")
                .resizable()
                .frame(width: 24, height: 24)
        })
    }
    
    @ViewBuilder
    private var PositiveButton: some View {
        CommonSmallButton(
            label: post.textPositiveButton,
            action: {
                post.positiveButtonClick()
                callback()
            },
            foregroundColor: Palette.white.swiftUIColor,
            backgroundColor: Palette.black.swiftUIColor,
            borderColor: Palette.white.swiftUIColor,
            borderWidth: 0
        )
    }
    
    @ViewBuilder
    private var NegativeButton: some View {
        Button(action: {
            post.negativeButtonClick()
            callback()
        }, label: {
            CommonText(post.textNegativeButton)
                .font(.hintSemibold)
                .foregroundColor(Palette.black.swiftUIColor)
        })
    }
}
