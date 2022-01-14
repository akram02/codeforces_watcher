import SwiftUI

struct VideoView: View {
    
    var body: some View {
        VStack(spacing: 12) {
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
            
            Image("video_placeholder")
                .resizable()
                .frame(maxWidth: .infinity)
                .scaledToFit()
                .cornerRadius(10)
        }
        .frame(maxWidth: .infinity)
        .padding(12)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding(.top, 20)
    }
}

struct VideoView_Previews: PreviewProvider {
    static var previews: some View {
        VideoView()
    }
}
