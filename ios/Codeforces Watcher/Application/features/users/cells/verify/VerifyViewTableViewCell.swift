import SwiftUI

struct VerifyViewTableViewCell: View {
    var body: some View {
        VStack(alignment: .leading, spacing: 2) {
            Image("avatar")
            
            CommonText("Verify Account")
                .font(.subHeaderMedium2)
                .foregroundColor(Palette.black.swiftUIColor)
            
            
            HStack(alignment: .bottom, spacing: 0) {
                CommonText("Pass the quick and simple verification to see your profile here")
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                
                Spacer()
                    .frame(width: 48)
                
                SmallCommonButton(
                    label: "Verify".uppercased(),
                    action: {},
                    foregroundColor: Palette.black.swiftUIColor,
                    backgroundColor: Color.clear,
                    borderColor: Palette.black.swiftUIColor,
                    borderWidth: 1.6
                )
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity)
        .background(Palette.accentGrayish.swiftUIColor)
        .cornerRadius(20)
        .padding(20)
    }
}

struct VerifyViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        VerifyViewTableViewCell()
    }
}
