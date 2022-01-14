import SwiftUI

struct PostTitleView: View {
    
    var body: some View {
        HStack(spacing: 8) {
            Image("noImage")
            
            VStack(alignment: .leading, spacing: 0) {
                CommonText("Matrix Exponentiation Coding")
                    .font(.bodySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                HStack(spacing: 0) {
                    CommonText("errichto")
                        .foregroundColor(Palette.red.swiftUIColor)
                    
                    CommonText(" · 5 minutes ago")
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                }
                .font(.hintRegular)
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity)
        }
    }
}

struct PostTitleView_Previews: PreviewProvider {
    static var previews: some View {
        PostTitleView()
    }
}
